<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:aws="http://webservices.amazon.com/AWSECommerceService/2005-10-05" version="1.0">
    <xsl:output  method="text" indent="yes" />
    
    <xsl:variable name="startBrack"><![CDATA[<]]></xsl:variable>
    <xsl:variable name="endBrack"><![CDATA[>]]></xsl:variable>
    <xsl:variable name="pound"><![CDATA[#]]></xsl:variable>
    
    <xsl:template match="/">
        <xsl:variable name="itemSearchIndex" select="/aws:ItemSearchResponse/aws:Items/aws:Request/aws:ItemSearchRequest/aws:SearchIndex"/>
        <xsl:choose>
            <xsl:when test="$itemSearchIndex = 'Music'"><xsl:call-template name="Music" /></xsl:when>
            <xsl:when test="$itemSearchIndex = 'DVD'"><xsl:call-template name="DVD" /></xsl:when>
            <xsl:when test="$itemSearchIndex = 'Books'"><xsl:call-template name="Books" /></xsl:when>
        </xsl:choose>
    </xsl:template>
    
    <xsl:template name="Music">
        <xsl:variable name="artist_subject" select="/aws:ItemSearchResponse/aws:Items/aws:Request/aws:ItemSearchRequest/aws:Artist"/>
        <xsl:variable name="artist_property" select="'isSubClass'"/>
        <xsl:variable name="artist_object" select="concat($pound,'Artist')"/>
        <xsl:value-of disable-output-escaping="yes" select="concat($startBrack,$artist_subject,$endBrack,' ')"/>
        <xsl:value-of disable-output-escaping="yes" select="concat($startBrack,$artist_property,$endBrack,' ')"/>
        <xsl:value-of disable-output-escaping="yes" select="concat($startBrack,$artist_object,$endBrack,' .','&#xA;')"/>
        
        <xsl:value-of disable-output-escaping="yes" select="'&#xA;'"/> <!-- Newline for readibility-->
        
        <xsl:for-each select="/aws:ItemSearchResponse/aws:Items/aws:Item">
            <xsl:variable name="title_subject" select="child::aws:ItemAttributes/aws:Title"/>
            <xsl:variable name="title_property" select="'isSubClass'"/>
            <xsl:variable name="title_object" select="concat($pound,'CD')"/>
            <xsl:value-of disable-output-escaping="yes" select="concat($startBrack,$title_subject,$endBrack,' ')"/>
            <xsl:value-of disable-output-escaping="yes" select="concat($startBrack,$title_property,$endBrack,' ')"/>
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
