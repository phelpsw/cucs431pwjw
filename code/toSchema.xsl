<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:cs431="http://cs431.org/mashup#" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:atom2="http://purl.org/atom/ns#" xmlns:atom="http://www.w3.org/2005/Atom"
    xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:oai="http://www.openarchives.org/OAI/2.0/"
    xmlns:nsdl="http://ns.nsdl.org/search/rest_v2.00/" xmlns:dct="http://purl.org/dc/terms/"
    version="1.0">
    <xsl:template match="/">
        <xsl:element name="cs431:mashup" namespace="http://cs431.org/mashup#">
            <xsl:attribute name="xmlns:cs431">http://cs431.org/mashup#</xsl:attribute>
            <xsl:attribute name="xmlns:xsi">http://www.w3.org/2001/XMLSchema-instance</xsl:attribute>
            <xsl:attribute name="xmlns:atom">http://www.w3.org/2005/Atom</xsl:attribute>
            <xsl:attribute name="xmlns:dc">http://purl.org/dc/elements/1.1/</xsl:attribute>
            <xsl:attribute name="xmlns:oai">http://www.openarchives.org/OAI/2.0/</xsl:attribute>
            <xsl:attribute name="xmlns:nsdl">http://ns.nsdl.org/search/rest_v2.00/</xsl:attribute>
            <xsl:attribute name="xmlns:dct">http://purl.org/dc/terms/</xsl:attribute>
            <xsl:attribute name="xsi:schemaLocation">http://cs431.org/mashup# mashupExtension.xsd</xsl:attribute>
            <xsl:element name="cs431:mashupMetadata">
                <xsl:element name="dc:title">
                    <xsl:variable name="queryString"
                        select=" substring-before(substring-after(/atom2:feed/atom2:link/@href,'%22'),'%22')"/>
                    <xsl:if test="contains($queryString,'+')">
                        <xsl:call-template name="removePlus">
                            <xsl:with-param name="query">
                                <xsl:value-of select="$queryString"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                    <xsl:if test="not(contains($queryString,'+'))">
                        <xsl:value-of select="$queryString"/>
                    </xsl:if>
                </xsl:element>
                <xsl:element name="dc:creator">Justin and Phelps</xsl:element>
                <xsl:element name="dc:description">A Mashup</xsl:element>
            </xsl:element>
            <!--<xsl:apply-templates select="document('google.xml')" mode="google"/>
                <xsl:apply-templates select="document('yahoo.xml')" mode="yahoo" /> -->            
             <xsl:apply-templates select="document('nsdl.xml')" mode="nsdl"/>

        </xsl:element>
    </xsl:template>

    <xsl:template match="/nsdl:NSDLSearchService" mode="nsdl">
        <xsl:for-each select="nsdl:SearchResults/nsdl:results/nsdl:document">
            <xsl:element name="cs431:querySource">
                <!-- Get query URI -->
                <xsl:element name="cs431:loc">
                    <xsl:value-of select="nsdl:header/nsdl:resourceIdentifier"/>
                </xsl:element>
                <xsl:element name="cs431:nsdlMetadata">
                    <xsl:element name="cs431:nsdlItem">
                        <xsl:for-each select="nsdl:fields/child::*">
                            <xsl:if test="namespace-uri(.)='http://purl.org/dc/elements/1.1/'">
                                <xsl:copy>
                                    <xsl:copy-of select="* | node()"/>
                                </xsl:copy>
                            </xsl:if>
                            <xsl:if test="namespace-uri(.)='http://ns.nsdl.org/search/rest_v2.00/'">
                                <!-- This copies the element, not set in the correct namespace yet "nsdl:" -->
                                <xsl:copy>
                                    <!--  This copies the body of the nsdl elements -->
                                    <xsl:copy-of select="@* | node()"/>
                                </xsl:copy>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="/rss" mode="yahoo">
        <xsl:for-each select="/rss/channel/item">
            <xsl:element name="cs431:querySource">
                <xsl:attribute name="query"><!-- Get query URI --></xsl:attribute>
                <xsl:element name="cs431:loc">
                    <xsl:value-of select="/rss/channel/link"/>
                </xsl:element>
                <xsl:element name="cs431:rssMetadata">
                    <xsl:element name="cs431:rssItem">
                        <xsl:copy-of select="node()"/>
                    </xsl:element>
                </xsl:element>
            </xsl:element>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="/atom2:feed" mode="google">
        <xsl:for-each select="atom2:entry">
            <xsl:element name="cs431:querySource">
                <xsl:attribute name="query"><!-- Get query URI --></xsl:attribute>
                <xsl:element name="cs431:loc">
                    <xsl:value-of select="atom2:link/@href"/>
                </xsl:element>
                <xsl:element name="cs431:atomMetadata">
                    <xsl:call-template name="copyAtom"/>
                </xsl:element>
            </xsl:element>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="copyAtom">
        <xsl:element name="cs431:atomItem">
        <xsl:for-each select="child::*">
                <xsl:element name="{concat('atom:',local-name(.))}">
                    <xsl:copy-of select="@* | node()" />
                </xsl:element>

        </xsl:for-each>
        </xsl:element>
    </xsl:template>

    <xsl:template name="removePlus">
        <xsl:param name="query"/>
        <xsl:variable name="before" select="substring-before($query,'+')"/>
        <xsl:variable name="after" select="concat(' ', substring-after($query,'+'))"/>
        <xsl:variable name="final" select="concat($before,$after)"/>
        <xsl:if test="contains($final,'+')">
            <xsl:call-template name="removePlus">
                <xsl:with-param name="query">
                    <xsl:value-of select="$final"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="not(contains($final,'+'))">
            <xsl:value-of select="$final"/>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
