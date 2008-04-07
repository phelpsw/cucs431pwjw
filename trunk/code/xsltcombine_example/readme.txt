CS431 Project 1 Readme

If Python 2.5.1 with libxml is installed, run the script as follows (it likely works with older versions of libxml and Python as well though this has not been tested libxml 2.6.30+, libxslt 1.1.22+ for python 2.5):

python mashupSearch.py Global Warming

This will execute a search on "Global Warming" and dump the results to output.xml

If a server is setup which supports python cgi scripts the search interface can be accessed:

{your_server}/mashupSearch.py

This will show a search form in the browser.  Clicking submit will cause all the transforms occur and the user is forwarded to results.html which shows the output of toXHTML.xsl.

The only outstanding problem is due to some odd permissions issues, when switching between the two runtime approaches (cmdline vs web), all output and temporary files must be deleted.  The reason is because the cmdline version can't overwrite google.xml for example which is owned by the webserver and vice versa.  The following files must be deleted when making this transition:

google.xml
nsdl.xml
output.xml
queryholder.xml
results.html
yahoo.xml

That is it.
