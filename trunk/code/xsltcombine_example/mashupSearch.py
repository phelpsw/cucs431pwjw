#!C:\Python25\python.exe

import cgi, string, sys
import os
import urllib
import urllib2
import libxml2
import libxslt

def http_main():
	form = cgi.FieldStorage()
	if (form.has_key("action") and form.has_key("searchquery")):
		if (form["action"].value == "display"):
			# perform search and generate xml files to transform
			performSearchQuery(form["searchquery"].value)
			# perform transformation
			performTransformationXML()
			performTransformationHTML()
			performHTMLTidy()
			# redirect to data
			print_redirect("results.html")
			
	else:
		generate_form()

def performHTMLTidy():
	os.system("tidy.exe --clean y --doctype \"strict\" --output-xhtml y --indent \"auto\" --wrap \"90\" results.html results.html")

def performTransformationXML():
	styledoc = libxml2.parseFile("toSchema.xsl")
	style = libxslt.parseStylesheetDoc(styledoc)
	doc = libxml2.parseFile("google.xml")
	result = style.applyStylesheet(doc, None)
	FILE = open("output.xml","w")
	FILE.write(result.serialize())
	FILE.close()

def performTransformationHTML():
	styledoc = libxml2.parseFile("toXHTML.xsl")
	style = libxslt.parseStylesheetDoc(styledoc)
	doc = libxml2.parseFile("output.xml")
	result = style.applyStylesheet(doc, None)
	FILE = open("results.html","w")
	FILE.write(result.serialize())
	FILE.close()
	
def print_redirect(url):
	print 'Status: 302 Moved Temporarily'
	print 'Location:', url
	print 'Pragma: no-cache'
	print 'Content-Type: text/html'
	print
	
	print '<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML//EN">'
	print '<title>Redirect (302)</title>'
	print '<h1>302 Moved Temporarily</h1>'
	print '<p>The answer to your request is located at'
	hurl = cgi.escape(url, 1)
	print '<a href="%s">%s</a>.' % (hurl, hurl)

def generate_form():
	print "Content-type: text/html\n"
	print "<HTML>\n"
	print "<HEAD>\n"
	print "\t<TITLE>Mashup Query</TITLE>\n"
	print "</HEAD>\n"
	print "<BODY BGCOLOR = white>\n"
	print "\t<FORM METHOD = post ACTION = \"mashupSearch.py\">\n"
	print "\t<TABLE BORDER = 0>\n"
	print "\t\t<TR><TH>Mashup Query:</TH><TD><INPUT TYPE = text NAME = \"searchquery\"></TD></TR>"
	print "\t</TABLE>"
	print "\t<INPUT TYPE = hidden NAME = \"action\" VALUE = \"display\">"
	print "\t<INPUT TYPE = submit VALUE = \"Search\">\n"
	print "\t</FORM>\n"
	print "</BODY>\n"
	print "</HTML>\n"

def performSearchQuery(queryTopic):

	# Google News lookup
	data = urllib.urlencode({'q' : queryTopic, 'output' : 'atom'})
	urigoogle = "http://news.google.com/news?" + data
	req=urllib2.Request(url=urigoogle, headers={'User-Agent':'Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)'})
	response = urllib2.urlopen(req)
	FILE = open("google.xml","w")
	FILE.write(response.read())
	FILE.close()
	
	# Yahoo News lookup
	data = urllib.urlencode({'p' : queryTopic})
	uriyahoo = "http://news.search.yahoo.com/news/rss?" + data
	req=urllib2.Request(url=uriyahoo, headers={'User-Agent':'Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)'})
	response = urllib2.urlopen(req)
	FILE = open("yahoo.xml","w")
	FILE.write(response.read())
	FILE.close()
	
	# NSDL News Lookup
	data = urllib.urlencode({'q' : queryTopic, 'n' : '10', 's' : '0'})
	urinsdl = "http://ndrsearch.nsdl.org/search?" + data
	req=urllib2.Request(url=urinsdl, headers={'User-Agent':'Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)'})
	response = urllib2.urlopen(req)
	FILE = open("nsdl.xml","w")
	FILE.write(response.read())
	FILE.close()
	
	FILE = open("queryholder.xml","w")
	FILE.write("<?xml version=\"1.0\" encoding=\"utf-8\"?><queryinfo>")
	FILE.write("<queryTarget>"+queryTopic+"</queryTarget>")
	FILE.write("<query src=\"yahoo\" query=\""+urllib.quote_plus(uriyahoo)+"\"/>")
	FILE.write("<query src=\"google\" query=\""+urllib.quote_plus(urigoogle)+"\"/>")
	FILE.write("<query src=\"nsdl\" query=\""+urllib.quote_plus(urinsdl)+"\"/>")
	FILE.write("</queryinfo>")
	FILE.close()

if 'SERVER_SOFTWARE' in os.environ:
     # CGI script
	http_main()
else:
	# Running from command line
	inputlist = sys.argv[1::] # ignore first entry
	if(len(inputlist) <= 0):
		print "Error, no search parameters"
		sys.exit()
	queryString = string.join(inputlist, " ")
	print "Searching for \"" + queryString + "\""
	performSearchQuery(queryString)
	performTransformationXML()
"""
try:
	os.remove('google.xml')
	os.remove('yahoo.xml')
	os.remove('nsdl.xml')
	os.remove('queryholder.xml')
except Exception:
	sys.exit()
"""
	