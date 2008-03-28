
import time
st = time.time()

import libxml2, libxslt
import cgi, os, sys
import urllib
import urllib2
#query = cgi.FieldStorage()


readtimestart = time.time()

queryTopic = "Global Warming"

# Google News lookup
data = urllib.urlencode({'q' : queryTopic, 'output' : 'atom'})
req=urllib2.Request(url="http://news.google.com/news?" + data, headers={'User-Agent':'Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)'})
response = urllib2.urlopen(req)
#print response.read()

# Yahoo News lookup
data = urllib.urlencode({'p' : queryTopic})
req=urllib2.Request(url="http://news.search.yahoo.com/news/rss?" + data, headers={'User-Agent':'Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)'})
response = urllib2.urlopen(req)
#print response.read()



# NSDL News Lookup
data = urllib.urlencode({'q' : queryTopic, 'n' : '10', 's' : '0'})
req=urllib2.Request(url="http://ndrsearch.nsdl.org/search?" + data, headers={'User-Agent':'Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)'})
response = urllib2.urlopen(req)
#print response.read()

#FILE = open("test.xml","w")
#FILE.write(response.read())
#FILE.close()

"""
src1 = urllib.urlopen("http://news.google.com/news?%s" % data)
print src1.read()
src2 = urllib.urlopen("http://news.search.yahoo.com/news/rss?p="+queryTopic)
print src2
src3 = urllib.urlopen("http://ndrsearch.nsdl.org/search?n=10&q="+queryTopic+"&s=0")
print src3
"""




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
