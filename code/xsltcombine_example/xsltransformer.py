
import time
st = time.time()

import libxml2, libxslt
import cgi, os, sys
import urllib
import urllib2

readtimestart = time.time()

queryTopic = "Global Warming"

# Google News lookup
data = urllib.urlencode({'q' : queryTopic, 'output' : 'atom'})
urigoogle = "http://news.google.com/news?" + data
req=urllib2.Request(url=urigoogle, headers={'User-Agent':'Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)'})
response = urllib2.urlopen(req)
#print response.read()
FILE = open("google.xml","w")
FILE.write(response.read())
FILE.close()

# Yahoo News lookup
data = urllib.urlencode({'p' : queryTopic})
uriyahoo = "http://news.search.yahoo.com/news/rss?" + data
req=urllib2.Request(url=uriyahoo, headers={'User-Agent':'Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)'})
response = urllib2.urlopen(req)
#print response.read()
FILE = open("yahoo.xml","w")
FILE.write(response.read())
FILE.close()

# NSDL News Lookup
data = urllib.urlencode({'q' : queryTopic, 'n' : '10', 's' : '0'})
urinsdl = "http://ndrsearch.nsdl.org/search?" + data
req=urllib2.Request(url=urinsdl, headers={'User-Agent':'Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)'})
response = urllib2.urlopen(req)
#print response.read()
FILE = open("nsdl.xml","w")
FILE.write(response.read())
FILE.close()

FILE = open("queryholder.xml","w")
FILE.write("<?xml version=\"1.0\" encoding=\"utf-8\"?><queryinfo>")
FILE.write("<query src=\"yahoo\" query=\""+urllib.quote_plus(uriyahoo)+"\"/>")
FILE.write("<query src=\"google\" query=\""+urllib.quote_plus(urigoogle)+"\"/>")
FILE.write("<query src=\"nsdl\" query=\""+urllib.quote_plus(urinsdl)+"\"/>")
FILE.write("</queryinfo>")
FILE.close()

styledoc = libxml2.parseFile("shirtColors.xsl")
style = libxslt.parseStylesheetDoc(styledoc)
#doc = libxml2.parseFile(query['script'].value)
doc = libxml2.parseFile("shirts.xml")
readtimeend = time.time()


start_converting = time.time()
result = style.applyStylesheet(doc, None)
done_converting = time.time()

html = result.serialize()

print "Content-type: text/html"
print
print html

style.freeStylesheet()
doc.freeDoc()
result.freeDoc()

et = time.time()
totaltime = et-st
print "<!-- Page served in %s seconds. -->" % totaltime
print "<!-- XML conversion took %s seconds. -->" %\
(done_converting-start_converting)
print "<!-- File reading took %s seconds. -->" %\
(readtimeend-readtimestart)

input("Press Enter to Quit...")
