<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:cs431="http://cs431.org/mashup#" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:atom2="http://purl.org/atom/ns#" xmlns:atom="http://www.w3.org/2005/Atom"
    xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:oai="http://www.openarchives.org/OAI/2.0/"
    xmlns:nsdl="http://ns.nsdl.org/search/rest_v2.00/" xmlns:dct="http://purl.org/dc/terms/" version="1.0">
    <xsl:output  method="xml" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN" indent="yes" />
    <xsl:template match="/">
        <!-- get xmlns attribute to display only on <html> tag -->
        <xsl:element name="html" namespace="http://www.w3.org/1999/xhtml">
            <xsl:attribute name="xml:lang">en</xsl:attribute>
            <xsl:attribute name="lang">en</xsl:attribute>
            <xsl:element name="head">
                <xsl:element name="title"><xsl:value-of select="/cs431:mashup/cs431:mashupMetadata/dc:title"/></xsl:element>
                <xsl:element name="link">
                    <xsl:attribute name="rel">stylesheet</xsl:attribute>
                    <xsl:attribute name="type">text/css</xsl:attribute>
                    <xsl:attribute name="href">global.css</xsl:attribute>
                </xsl:element>
            </xsl:element>
            <xsl:element name="body" namespace="">
                <xsl:call-template name="body" />
            </xsl:element>
        </xsl:element>

    </xsl:template>
    <xsl:template name="body">
        <xsl:element name="h1"><xsl:value-of select="concat('CS431 Mashup: ',/cs431:mashup/cs431:mashupMetadata/dc:title)"/></xsl:element>
        <xsl:element name="div">
            <xsl:attribute name="id">nav</xsl:attribute>
            <xsl:element name="a">
                <xsl:attribute name="href">#yahoo</xsl:attribute>
                Yahoo!
            </xsl:element> |
            <xsl:element name="a">
                <xsl:attribute name="href">#google</xsl:attribute>
                Google
            </xsl:element> |
            <xsl:element name="a">
                <xsl:attribute name="href">#nsdl</xsl:attribute>
                NSDL
            </xsl:element>
        </xsl:element>
        <xsl:call-template name="items" />
    </xsl:template>

    <xsl:template name="items">
        <xsl:element name="div">
            <xsl:attribute name="id">yahoo</xsl:attribute>
            <xsl:attribute name="class">feed</xsl:attribute>
            <xsl:element name="h2">Yahoo!</xsl:element>
            <xsl:for-each select="/cs431:mashup/cs431:querySource[contains(@query,'yahoo')]">
                <xsl:call-template name="yahoo" />
        </xsl:for-each>
        </xsl:element>

        <xsl:element name="div">
            <xsl:attribute name="id">google</xsl:attribute>
            <xsl:attribute name="class">feed</xsl:attribute>
            <xsl:element name="h2">Google</xsl:element>
            <xsl:for-each select="/cs431:mashup/cs431:querySource[contains(@query,'google')]">
                <xsl:call-template name="google" />
        </xsl:for-each>
        </xsl:element>
        
        <xsl:element name="div">
            <xsl:attribute name="id">nsdl</xsl:attribute>
            <xsl:attribute name="class">feed</xsl:attribute>
            <xsl:element name="h2">NSDL</xsl:element>
        <xsl:for-each select="/cs431:mashup/cs431:querySource[contains(@query,'nsdl')]">
                <xsl:call-template name="nsdl" />
        </xsl:for-each>
        </xsl:element>
    </xsl:template>
    
    <xsl:template name="yahoo">
        <xsl:element name="div">
            <xsl:attribute name="class">article</xsl:attribute>
            <xsl:element name="div">
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:value-of select="cs431:loc"/>
                    </xsl:attribute>
                    <xsl:value-of select="cs431:rssMetadata/cs431:rssItem/title"/>
                </xsl:element>                
            </xsl:element>
            <xsl:element name="p">
                <xsl:value-of select="cs431:rssMetadata/cs431:rssItem/description"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:template name="google">
        
        <!--<xsl:element name="div">
            <xsl:attribute name="class">article</xsl:attribute>
            <xsl:element name="div">
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:value-of select="cs431:loc"/>
                    </xsl:attribute>
                    <xsl:value-of select="cs431:atomMetadata/cs431:atomItem/atom:title" />
                </xsl:element>                
            </xsl:element>
            <xsl:element name="p">
                <xsl:value-of select="cs431:atomMetadata/cs431:atomItem/atom:content"/>
            </xsl:element>
            </xsl:element>-->
         <xsl:value-of select="cs431:atomMetadata/cs431:atomItem/atom:content" disable-output-escaping="yes"/>
    </xsl:template>
    
    <xsl:template name="nsdl">
        <xsl:element name="div">
            <xsl:attribute name="class">article</xsl:attribute>
            <xsl:element name="div">
                <xsl:element name="a">
                    <xsl:attribute name="href">
                        <xsl:value-of select="cs431:loc"/>
                    </xsl:attribute>
                    <xsl:value-of select="cs431:nsdlMetadata/cs431:nsdlItem/nsdl:compoundTitle"/>
                </xsl:element>                
            </xsl:element>
            <xsl:element name="p">
                <xsl:value-of select="cs431:nsdlMetadata/cs431:nsdlItem/nsdl:compoundDescription"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
</xsl:stylesheet>
