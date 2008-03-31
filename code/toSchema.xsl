<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="/">
        <cs431:mashup xmlns:cs431="http://cs431.org/mashup#"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:atom="http://www.w3.org/2005/Atom"
            xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:oai="http://www.openarchives.org/OAI/2.0/"
            xmlns:nsdl="http://ns.nsdl.org/search/rest_v2.00/" xmlns:dct="http://purl.org/dc/terms/"
            xsi:schemaLocation="http://cs431.org/mashup# mashupExtension.xsd">
            <cs431:mashupMetadata>
                <dc:title>Global Warming</dc:title>
                <dc:creator>Carl Lagoze</dc:creator>
                <dc:description>A Mashup of information about Global Warming</dc:description>
            </cs431:mashupMetadata>
            <xsl:apply-templates select="document('google.xml')" />
            <xsl:apply-templates select="document('yahoo.xml')" />
            <xsl:apply-templates select="document('nsdl.xml')" />
        </cs431:mashup>
    </xsl:template>
    <xsl:template match="feed">
        <cs431:querySource>
            <xsl:attribute name="query"><xsl:value-of select=""/></xsl:attribute>
        </cs431:querySource>
        <cs431:loc>
            <xsl:value-of select="entry/link/@href"/>
        </cs431:loc>
        
    </xsl:template>
</xsl:stylesheet>
