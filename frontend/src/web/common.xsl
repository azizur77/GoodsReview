<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:template name="find">
        <tr align="center">
            <td colspan="3">
                <form method="get" action="/search.xml">
                    <input type="text" name="query" size="100"/>
                    <input type="submit" value="Find"/>
                </form>
            </td>
        </tr>
    </xsl:template>

    <xsl:template name="title">
        <title>
            <xsl:text>Goods Review</xsl:text>
        </title>
    </xsl:template>

    <xsl:template name="logo">
        <img id="logo" src="goodsReview.png" width="150" height="150"/>
    </xsl:template>


    <xsl:template name="greeting">
        <h1>
            <a href="/index.xml">Goods Review</a>
        </h1>
    </xsl:template>

    <xsl:template name="product">
        <hr/>
        <a>
            <xsl:attribute name="href">product.xml?id=<xsl:value-of select="@id"/>
            </xsl:attribute>
            <xsl:value-of select="name"/>
        </a>
        <br/>
        Description:
        <xsl:value-of select="description"/>
        <br/>
        Category:
        <xsl:value-of select="category"/>
        <br/>
        CategoryId:
        <xsl:value-of select="@category-id"/>
        <br/>
        Popularity:
        <xsl:value-of select="@popularity"/>
        <br/>
        Id:
        <xsl:value-of select="@id"/>
        <br/>
    </xsl:template>

    <xsl:template name="popular">
        <h4>Popular queries</h4>
        <xsl:for-each select="//product-for-view">
            <xsl:call-template name="product"/>
        </xsl:for-each>
        <hr/>
    </xsl:template>

    <xsl:template name="citilink-review">
        Comment:
        <xsl:value-of select="comment"/>
        <br/>
        Description:
        <xsl:value-of select="description"/>
        <br/>
        Helpfulness-yes:
        <xsl:value-of select="@helpfulness-yes"/>
        <br/>
        Helpfulness-no:
        <xsl:value-of select="@helpfulness-no"/>
        <br/>
        Rate:
        <xsl:value-of select="@rate"/>
        <br/>
    </xsl:template>

    <xsl:template name="thesis">
        Thesis:
        <xsl:value-of select="content"/>
        <br/>
        positivity:
        <xsl:value-of select="@positivity"/>
        <br/>
        votes-no:
        <xsl:value-of select="@votes-no"/>
        <br/>
        votes-yes:
        <xsl:value-of select="@votes-yes"/>
        <br/>
        importance:
        <xsl:value-of select="@importance"/>
        <br/>
    </xsl:template>

    <xsl:template name="review">
        Thesis:
        <xsl:value-of select="content"/>
        <br/>
        Author:
        <xsl:value-of select="author"/>
        <br/>
        description:
        <xsl:value-of select="description"/>
        <br/>
        Source-url:
        <xsl:value-of select="source-url"/>
        <br/>
        positivity:
        <xsl:value-of select="@positivity"/>
        <br/>
        votes-no:
        <xsl:value-of select="@votes-no"/>
        <br/>
        votes-yes:
        <xsl:value-of select="@votes-yes"/>
        <br/>
        importance:
        <xsl:value-of select="@importance"/>
        <br/>
    </xsl:template>
</xsl:stylesheet>