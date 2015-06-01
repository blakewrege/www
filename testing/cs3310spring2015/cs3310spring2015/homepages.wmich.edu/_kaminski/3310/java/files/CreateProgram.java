// APPLICATION:  RandomAccess       PROGRAM:  CreateProgram
// AUTHOR:  D. Kaminski
// DESCRIPTION:  This creates the random access file from data coming from an input file.
// NOTE:  The discussion below is referring to the RANDOM ACCESS FILE, not the input
//      file used in Create.
// FILE STRUCTURE (for the random access file):  DIRECT ADDRESS
//      This means that the location where a record is placed in the file, (the record's
//      "relative record number" or RRN) is taken DIRECTLY from a field in the record.
// PRIMARY KEY FIELD: In this example the ID field is the key used to map records to
//      file locations.  RRN is the ID, which is the 1st 2 digits in the record.
//      That is, the record with ID 06 is mapped to RRN 06, so is written to location 06.
// RRN's START AT 1, NOT 0:  For Java, RRN is a "logical concept" (just a pretend view),
//      not a "physical concept" (something really REAL, like arrays start at [0]).
//      Hence, RRN's could start anywhere the designer wanted.  But common usage refers
//      to locations 1, 2, 3, . . . (the 1st, 2nd, 3rd, ... spots in the relative file).
//      So RRN's start at 1 for this example.
// FILE ACCESS:  RANDOM ACCESS, so it calls the WriteARec method which requires an RRN.
//      This is used since the records in the inFile are in no particular order, in
//      terms of ID.
// SEEKing FOR RANDOM ACCESS:  Any time that random access is used (in ReadARec (#2
//      which uses RRN) and WriteARec - used by CreateProgram and QueryProgram, but not
//      in DumpProgram), the program must:
//          - calculate the desired offset based on RRN:
//                  offset = (RRN -1) * REC_SIZE;
//          - then seek to that location, a byte# relative to the start of the file:
//                  f.seek(offset);
//          - then do the Read or Write to the file
//      Several important things to note:
//          1) we need to know REC_SIZE which is a constant, so we need FIXED-LENGTH
//              record locations, so we use fixed-length records and fixed-length
//              fields.  For example, when writing a record at RRN 6, we need to leave
//              room in the file in case records will be written to RRN's 1 to 5 later.
//              If variable-length records were used, we wouldn't know what offset to
//              use when seeking before writing the record at location 6.
//          2) RandomAccessFile has a Seek method, InputStreamReader and OutputStreamWriter 
//              do not. So we have to use RandomAccessFile.  Its Read and Write methods 
//              deal with byteArrays, not strings.  So encoding/decoding usually have to 
//              be done convert the buffer to/from a string, aLine.  (aLine then may need
//              splitting into substrings to get the fields after reading/encoding,
//              and later fields combined into aLine before writing/decoding).
//          3) RandomAccessFile needs to use FIXED-LENGTH records because the Read and Write
//              method need the REC_SIZE parameter.  (This is OK since a random access
//              file needs fixed-length locations anyway).
//      NOTE:  There are exceptions to the above, as will be noted in class.
// EMPTY LOCATIONS ("HOLES"):  Some locations will never have a record written to them.
//      This includes 2 types of locations, which each have a different way of the
//      program detecting them:
//          1) locations within the allocated file space for this file, between other
//              good records - the "all-0-bits" case.  (The system initializes the
//              file space allocated for this file to all-0-bits which is interpreted
//              as all '\0' characters in a char or string field, or as the value 0 in
//              an int or float field).
//          2) locations past the EOF, beyond the file space allocated for this file -
//              the "past EOF" case
//      Create doesn't do hole-checking.
//      Query & Dump both care about checking for holes.
// NO HOLE-CHECKING IN CREATE:  In this example, CreateProgram does not care about
//      checking for an empty location before a record is written in that spot -
//      it just writes the record to that location.  This is not generally the best way
//      to handle things - a subsequent demo example will demonstrate "hole-checking" in
//      Create.
// NO &lt;CR>&lt;LF> IN THE FILE:  The random file does not contain any &lt;CR>&lt;LF>'s at the ends
//      of records.  This is in part because BufferedReader's ReadLine strips them off
//      and just returns the DATA portion of the record, so that's what's handed to
//      WriteARec.  And since that method uses RandomAccessFile (which has a Write, not a
//      WriteLine method), no &lt;CR>&lt;LF> is added.
//          So when the file is viewed in NotePad, it's not a "normal" text file with
//      each record on a separate line.  If the specs had required a normal text file,
//      then WriteARec method could have done:       aLine = aLine + "\r\n";
//      before converting aLine to the buffer, which adds the &lt;CR>&lt;LF> to aLine.
//      However, that would put &lt;CR>&lt;LF>'s after GOOD records, but the empty locations
//      would not have &lt;CR>&lt;LF>'s, so this still wouldn't result in a normal text file
//      when viewed in NotePad.  An option to fix this would be to initialize the file
//      space ahead of time, but see the next "IMPORTANT - DON'T INITIALIZE" note below.
//          But, DumpProgram and QueryProgram (which call ReadARec) handle this since
//      ReadARec uses RandomAccessFile which has a Read (not a ReadLine, which would 
//      strip off the last 2 bytes of the line).  The 2 programs just use println 
//      when displaying the records on the console.
// DON'T INITIALIZE FILE SPACE:  It's generally not necessary (and indeed a complete
//      waste of expensive I/O time) to intialize the file space before starting to
//      insert records in the random file.  This is because we can trust the OS to
//      initialize any file space it allocates to this file to all 0 bits.  And the
//      program would have no way of knowing how big that for loop should be since it
//      has no idea what the largest ID will be in the input file - ahead of time 
//      before reading the input file.  [Only initialize the file space if you're using
//      an OS which does NOT guarantee to initialize it for you].
// DISCLAIMER:  Because this is a demo example, in order to highlight the new concepts,
//      I have kept the rest of the program simple.  There is no hole-checking.  There
//      is no separate record class.  Records are treated as just aLine rather than
//      dealing with their separate fields.
// *************************************************************************************

package randomaccess;

import java.io.*;

public class CreateProgram 
{
    public static void main(String[] args) throws FileNotFoundException, IOException 
    {
        //Sets up a file reader to read the file one character at a time
        FileReader inFile = new FileReader("InFile.txt");

        //Filter FileReader through a BufferedReader to read a line at a time
        BufferedReader input = new BufferedReader(inFile);
        
        RanFile ranFile = new RanFile("Create");
        
        String aLine;
        int RRN;                           // Relative Record Number

        while ((aLine = input.readLine()) != null) //loop through input 
                                                   // until End Of File
        {
            RRN = Integer.parseInt(aLine.substring(0, 2));  //(error checking 
                                               // really should be done on this)
            ranFile.WriteARec(aLine, RRN);
        }
        System.out.println("OK, Random File Created.  Check it in NotePad.  " +
            "Now Run DumpProgram, then QueryProgram");
           
        input.close();
        inFile.close();  
        ranFile.CloseFile();
    }
}
