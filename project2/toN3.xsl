<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:aws="http://webservices.amazon.com/AWSECommerceService/2005-10-05" 
    xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" 
    xml:base="http://www.owl-ontologies.com/Ontology1209425965.owl" version="1.0">
    <xsl:output  method="text"/>
    
    <xsl:variable name="startBrack"><![CDATA[<]]></xsl:variable>
    <xsl:variable name="endBrack"><![CDATA[>]]></xsl:variable>
    <xsl:variable name="pound"><![CDATA[#]]></xsl:variable>
    
    
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
        <xsl:variable name="subclass" select="'rdfs:subClassOf'"/>
        <xsl:variable name="owlBase" select="'http://www.owl-ontologies.com/Ontology1209425965.owl'"/>
        
        <xsl:variable name="artist_subject" select="/aws:ItemSearchResponse/aws:Items/aws:Request/aws:ItemSearchRequest/aws:Artist"/>
        <xsl:variable name="artist_object" select="concat('aws:','Artist')"/>
        <xsl:value-of disable-output-escaping="yes" select="concat($startBrack,$artist_subject,$endBrack,' ')"/>
        <xsl:value-of disable-output-escaping="yes" select="concat($startBrack,$subclass,$endBrack,' ')"/>
        <xsl:value-of disable-output-escaping="yes" select="concat($startBrack,$artist_object,$endBrack,' .','&#xA;')"/>
        
        <xsl:value-of disable-output-escaping="yes" select="'&#xA;'"/> <!-- Newline for readibility-->
        
        <xsl:for-each select="/aws:ItemSearchResponse/aws:Items/aws:Item">
            <xsl:variable name="title_subject" select="child::aws:ItemAttributes/aws:Title"/>
            <xsl:variable name="title_object" select="concat($owlBase, $pound,'CD')"/>
            <xsl:value-of disable-output-escaping="yes" select="concat($startBrack,$title_subject,$endBrack,' ')"/>
            <xsl:value-of disable-output-escaping="yes" select="concat($startBrack,$subclass,$endBrack,' ')"/>
            <xsl:value-of disable-output-escaping="yes" select="concat($startBrack,$title_object,$endBrack,' .','&#xA;')"/>
        </xsl:for-each>
        
        <xsl:value-of disable-output-escaping="yes" select="'&#xA;'"/> <!-- Newline for readibility-->
        
        <xsl:for-each select="/aws:ItemSearchResponse/aws:Items/aws:Item">
            <xsl:variable name="subject" select="$artist_subject"/>
            <xsl:variable name="property" select="'isPerformerIn'"/>
            <xsl:variable name="object" select="child::aws:ItemAttributes/aws:Title"/>
            <xsl:value-of disable-output-escaping="yes" select="concat($startBrack,$subject,$endBrack,' ')"/>
            <xsl:value-of disable-output-escaping="yes" select="concat($startBrack,$property,$endBrack,' ')"/>
            <xsl:value-of disable-output-escaping="yes" select="concat($startBrack,$object,$endBrack,' .','&#xA;')"/>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template name="DVD">
        
    </xsl:template>
    
    <xsl:template name="Books">
        
    </xsl:template>
    

</xsl:stylesheet>
