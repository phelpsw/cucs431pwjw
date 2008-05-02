<?xml version="1.0" encoding="UTF-8"?>

<!-- Useful info:
    http://www.w3.org/2000/10/swap/Primer.html
    http://www.w3.org/DesignIssues/Notation3
    http://getsemantic.com/wiki/Get_Started_with_the_Semantic_Web
    http://www.ibm.com/developerworks/library/w-rdf/
    http://www.xml.com/pub/a/2001/01/24/rdf.html
    http://kill.devc.at/node/84
    http://jena.sourceforge.net/javadoc/jena/rdfcat.html
    http://www.ibm.com/developerworks/xml/library/j-jena/index.html
    http://www.iandickinson.me.uk/articles/jena-tip-db-import/
    http://www.w3.org/TR/rdf-testcases/#ntriples
    http://www.ibm.com/developerworks/xml/library/x-think17/index.html
    -->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:aws="http://webservices.amazon.com/AWSECommerceService/2005-10-05" 
    xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" 
    xml:base="http://www.owl-ontologies.com/Ontology1209425965.owl" version="1.0">
    <xsl:output  method="text"/>
    
    <xsl:variable name="startBrack"><![CDATA[<]]></xsl:variable>
    <xsl:variable name="endBrack"><![CDATA[>]]></xsl:variable>
    <xsl:variable name="pound"><![CDATA[#]]></xsl:variable>
    <xsl:variable name="quote"><![CDATA["]]></xsl:variable>
    
    <xsl:template match="/">
        
        <xsl:text disable-output-escaping="yes" ><![CDATA[@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .]]>
<![CDATA[@prefix rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .]]>
<![CDATA[@prefix owl:  <http://www.w3.org/2002/07/owl#> .]]>
<![CDATA[@prefix aws: <http://www.owl-ontologies.com/Ontology1209425965.owl#> .]]>
        </xsl:text>
        
        <xsl:variable name="itemSearchIndex" select="/aws:ItemSearchResponse/aws:Items/aws:Request/aws:ItemSearchRequest/aws:SearchIndex"/>
        <xsl:choose>
            <xsl:when test="$itemSearchIndex = 'Music'"><xsl:call-template name="Music" /></xsl:when>
            <xsl:when test="$itemSearchIndex = 'DVD'"><xsl:call-template name="DVD" /></xsl:when>
            <xsl:when test="$itemSearchIndex = 'Books'"><xsl:call-template name="Books" /></xsl:when>
        </xsl:choose>
    </xsl:template>
    
    <xsl:template name="Music">
        
        <xsl:value-of disable-output-escaping="yes" select="'&#xA;'"/> <!-- Newline for readibility-->
        <xsl:variable name="subclass" select="'rdfs:subClassOf'"/> <!-- maybe should be rdf:type or even 'a' -->
        <xsl:variable name="owlBase" select="'http://www.owl-ontologies.com/Ontology1209425965.owl'"/>
        
        <xsl:variable name="artist_subject" select="/aws:ItemSearchResponse/aws:Items/aws:Request/aws:ItemSearchRequest/aws:Artist"/>
        <xsl:variable name="artist_object" select="'aws:Artist'"/>
        <xsl:value-of disable-output-escaping="yes" select="concat($quote,$artist_subject,$quote,' ')"/>
        <xsl:value-of disable-output-escaping="yes" select="concat($subclass,' ')"/>
        <xsl:value-of disable-output-escaping="yes" select="concat($artist_object,' .','&#xA;')"/>
        
        <xsl:value-of disable-output-escaping="yes" select="'&#xA;'"/> <!-- Newline for readibility-->
        
        <xsl:for-each select="/aws:ItemSearchResponse/aws:Items/aws:Item">
            <xsl:variable name="title_subject" select="child::aws:ItemAttributes/aws:Title"/>
            <xsl:variable name="title_object" select="'aws:CD'"/>
            <xsl:value-of disable-output-escaping="yes" select="concat($quote,$title_subject,$quote,' ')"/>
            <xsl:value-of disable-output-escaping="yes" select="concat($subclass,' ')"/>
            <xsl:value-of disable-output-escaping="yes" select="concat($title_object,' .','&#xA;')"/>
        </xsl:for-each>
        
        <xsl:value-of disable-output-escaping="yes" select="'&#xA;'"/> <!-- Newline for readibility-->
        
        <xsl:for-each select="/aws:ItemSearchResponse/aws:Items/aws:Item">
            <xsl:variable name="subject" select="$artist_subject"/>
            <xsl:variable name="property" select="'aws:isPerformerIn'"/>
            <xsl:variable name="object" select="child::aws:ItemAttributes/aws:Title"/>
            <xsl:value-of disable-output-escaping="yes" select="concat($quote,$subject,$quote,' ')"/>
            <xsl:value-of disable-output-escaping="yes" select="concat($property,' ')"/>
            <xsl:value-of disable-output-escaping="yes" select="concat($quote,$object,$quote,' .','&#xA;')"/>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template name="DVD">
        
    </xsl:template>
    
    <xsl:template name="Books">
        
    </xsl:template>
    

</xsl:stylesheet>
