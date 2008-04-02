<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:cs431="http://cs431.org/mashup#" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:atom="http://www.w3.org/2005/Atom"
    xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:oai="http://www.openarchives.org/OAI/2.0/"
    xmlns:nsdl="http://ns.nsdl.org/search/rest_v2.00/" xmlns:dct="http://purl.org/dc/terms/" version="1.0">
    <xsl:template match="/">
        <xsl:element name="cs431:mashup" namespace="http://cs431.org/mashup#">
            <xsl:attribute name="xmlns:cs431">http://cs431.org/mashup#</xsl:attribute>
            <xsl:attribute name="xmlns:xsi">http://www.w3.org/2001/XMLSchema-instance</xsl:attribute>
            <xsl:attribute name="xmlns:atom">http://www.w3.org/2005/Atom</xsl:attribute>
            <xsl:attribute name="dc">http://purl.org/dc/elements/1.1/</xsl:attribute>
            <xsl:attribute name="oai">http://www.openarchives.org/OAI/2.0/</xsl:attribute>
            <xsl:attribute name="nsdl">http://ns.nsdl.org/search/rest_v2.00/</xsl:attribute>
            <xsl:attribute name="dct">http://purl.org/dc/terms/</xsl:attribute>
            <xsl:attribute name="xsi:schemaLocation">http://cs431.org/mashup# mashupExtension.xsd</xsl:attribute>
            <xsl:element name="cs431:mashupMetadata">
                <xsl:element name="dc:title">
                    <xsl:variable name="queryString" select="/" />
                    <xsl:value-of select="feed/generator/text()"/>
                </xsl:element>
            </xsl:element>
        </xsl:element>        
    </xsl:template>

</xsl:stylesheet>
