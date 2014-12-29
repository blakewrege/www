#!/usr/bin/env python
"""BOILERBOT.py

This code is intended to be a simple boilerplate
for building a simple python bot.
To better understand whats goin on,
I recommend going through and commenting
every single line with its purpose in the bot.

Fully python 3 compatible but will also
run just fine on python 2

https://docs.python.org/3/
https://tools.ietf.org/html/rfc2812i
"""

import socket
import sys
reload(sys)
sys.setdefaultencoding('utf8')

HOST = "localhost"  # must be run from yakko to connect directly
PORT = 6667
NICK = "dumbbot"
IDENT = 'dumbbot'
REALNAME = 'dumbbot'
CHANNEL = "#hackathon"

SOCK = socket.socket()
print("Connecting to server: " + HOST)
SOCK.connect((HOST, PORT))


def sendraw(string):
    """encode and send to server without processing"""
    print('>', string.strip())
    SOCK.send(string.encode())


def privmsg(message):
    """Send a message to CHANNEL"""
    msg = "PRIVMSG %s :%s\r\n" % (CHANNEL, message)
    sendraw(msg)


def nick(nickname):
    """Set IRC nick"""
    sendraw("NICK %s\r\n" % nickname)


def user(ident, name):
    """Set IRC user"""
    sendraw("USER %s 0 * :%s\r\n" % (ident, name))


def join(chan):
    """Join IRC channel"""
    sendraw("JOIN %s\r\n" % chan)


def pong(response):
    """Send PONG response to server PING"""
    sendraw("PONG %s\r\n" % response)


def listen():
    """Listen forever on socket"""
    while 1:
        data = SOCK.recv(2048).decode()
        for line in data.splitlines():
            print(line)
            if 'PING' == line.split()[0]:
                pong(line.split()[1])
            if 'PRIVMSG' in line:
                if '!test' in line:
		    fo = open("file.txt", "wb")
		    fo.write( "this is working\n");
		    fo.close()
                if 'roll call' in line:
                    privmsg("poo you")

                if ':hi boilerbot' in line:
                    sender = line.split("!")[0][1:]
                    privmsg("%s: hi" % sender)
        	if '!write' in line:
            	    wstr = data.split("!write",1)
		    num_lines = sum(1 for line in open('read.txt'))
		    if num_lines == 0:
			text_file = open("read.txt", "a")
			text_file.write("\n")
			text_file.close()
			num_lines = num_lines+1
            	    text_file = open("read.txt", "a")
		    if wstr[1].strip() == "":
			privmsg("empty string")
		    if num_lines > 8:
		        privmsg("too many items")
		    else:
            	    	text_file.write(wstr[1])
            	    	privmsg("message set")
           	    text_file.close()
		if '!remove' in line:
		    wstr = data.split("!remove",1)
		    count = 0
		    num_lines2 = sum(1 for line in open('read.txt'))
		    f = open("read.txt","r")
		    lines = f.readlines()
		    f.close()
		    try:
		    	if int(wstr[1]) > 0 and int(wstr[1]) < num_lines2:
		    		f = open("read.txt","w")
		    		for line in lines:
            	              	   if count != int(wstr[1]):
		    	  	      f.write(line)
				   count = count + 1
				privmsg("message removed")	
		    	else:
				privmsg("does not exist")
		    except ValueError:
				privmsg("Not a number")	
		    f.close()


if __name__ == "__main__":
    nick(NICK)
    user(IDENT, REALNAME)
    join(CHANNEL)

    privmsg("Hello, I am %s" % NICK)
    print(NICK, "Running")

    listen()
