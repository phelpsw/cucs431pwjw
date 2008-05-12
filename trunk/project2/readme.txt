Readme for Project 2 By Justin Choi(jc626) & Phelps Williams (pww36)

==Howto Run==

We have prepared scripts to help automate proper configuration of classpaths etc.
These may require slight modification to work properly on your machine.

===Windows===

Enter the unzipped project directory, open run.bat in a text editor and verify the
location of your java installation.  Because of hardcoded dependencies in the software
it is important that run.bat is executed from this directory.  To start the software
navigate to the project directory from the commandline and execute run.bat or double 
click on run.bat

===OSX/Linux===

Enter the unzipped project directory, open run.sh in a text editor and verify the
location of your java installation.  Because of hardcoded dependencies in the software
it is important that run.sh is executed from this directory.  To start the software
navigate to the project directory from the commandline and execute ./run.sh

==Howto Use==

The first step is to load data into the model.  This is performed by adding AWS queries
into the system through the AWS Query interface.  For example search for 
"Lawrence Lessig" under the book type and click add.  The search runs in the UI thread
so the UI will appear to freeze for a few seconds while the search returns and the
results are parsed.

The Agent URI, Properties, Product URI, and List URI lists below the SPARQL Query box
will automatically be populated by each AWS Query.  For example, delete the default 
value of the WHERE clause in the SPARQL Query box.  Now assuming the search above was 
performed for Lawrence Lessig, click on his name in the Agent URI's list box. 
His URI should appear.

Lets find all books authored by Lessig in our ontology.  Now find isAuthorOf property in
the Properties list box.  Complete the clause by adding a "?x".  The entire where clause
should now appear as follows:
WHERE { <http://www.owl-ontologies.com/Ontology1209425965.owl#Lawrence+Lessig> aws:isAuthorOf ?x .}

Click Query and review the results.

To save the data loaded into the ontology in the menu at the top of the screen click 
Model -> Save , a save dialog will guide you through the rest of the save process.

To validate the current ontology in the menu at the top of the screen click 
Model -> Validate, the results will be printed to the text window at the bottom of the 
gui.  It should be both Valid and Clean.

==Example==

To verify the pellet reasoner is working as expected here is a test designed to show a transitive
property established between queries.

1. Search 'scarlett johansson' under DVD
2. Check isConnectedTo on Scarlett Johansson with the following query:

PREFIX aws: <http://www.owl-ontologies.com/Ontology1209425965.owl#>
SELECT DISTINCT ?x
WHERE {<http://www.owl-ontologies.com/Ontology1209425965.owl#Scarlett+Johansson> aws:isConnectedTo ?x.}

You will see Colin Firth and others but no Reese Witherspoon

3. Search 'reese witherspoon' under DVD
4. Check isConnectedTo on Reese Witherspoon with the following query:

PREFIX aws: <http://www.owl-ontologies.com/Ontology1209425965.owl#>
SELECT DISTINCT ?x
WHERE {<http://www.owl-ontologies.com/Ontology1209425965.owl#Reese+Witherspoon>  aws:isConnectedTo ?x.}

Scarlett Johansson and Colin Firth will be listed.


5. Go to Model -> Clear and repeat steps 3 and 4. Colin Firth will be listed, but not Scarlett Johansson.