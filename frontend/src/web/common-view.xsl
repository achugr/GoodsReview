<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:template name="title">
        <title>
            <xsl:text>Goods Review</xsl:text>
        </title>
    </xsl:template>

    <xsl:template name="style">
        <style type="text/css">
            /* Override some defaults */
            html, body {
            background-color: #eee;
            }
            body {
            padding-top: 40px; /* 40px to make the container go all the way to the bottom of the topbar */
            }
            .container > footer p {
            text-align: center; /* center align it with the container */
            }
            .container {
            width: 820px; /* downsize our container to make the content feel a bit tighter and more cohesive.
            NOTE:
            this removes two full columns from the grid, meaning you only go to 14 columns and not 16. */
            }

            /* The white background content wrapper */
            .content {
            background-color: #fff;
            padding: 20px;
            margin: 0 -20px; /* negative indent the amount of the padding to maintain the grid system */
            -webkit-border-radius: 0 0 6px 6px;
            -moz-border-radius: 0 0 6px 6px;
            border-radius: 0 0 6px 6px;
            -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.15);
            -moz-box-shadow: 0 1px 2px rgba(0,0,0,.15);
            box-shadow: 0 1px 2px rgba(0,0,0,.15);
            }

            /* Page header tweaks */
            .page-header {
            background-color: #f5f5f5;
            padding: 20px 20px 10px;
            margin: -20px -20px 20px;
            }

            /* Styles you shouldn't keep as they are for displaying this base example only */
            .content .span10,
            .content .span4 {
            min-height: 500px;
            }
            /* Give a quick and non-cross-browser friendly divider */
            .content .span4 {
            margin-left: 0;
            padding-left: 19px;
            border-left: 1px solid #eee;
            }
            .span3 {
            height:50px;
            }

            .input .xlarge {
                width: 500px;
                height:25px;
                margin-top:32px;
                margin-bottom:15px;
                margin-left: -150px;
            }
            .span1 {
            margin-top:29px;
            margin-left:370px;
            }

            .topMenu a{
            color:#BFBFBF;
            }
            .topMenu li:hover{
            background-color:#404040;
            }
            .topMenu .current {
            border-top: solid 2px red;
            }
            .topMenu .current a{
            color:white;
            font-weight:bold;
            margin-top:-2px;
            }
            .menu {
            background: #EEE;
            text-align: center;
            -webkit-border-radius: 3px;
            -moz-border-radius: 3px;
            border-radius: 3px;
            min-height: 30px;
            line-height: 30px;
            margin-bottom:40px;
            }
            .menu .nav li:hover{
            background-color:#CCC;
            }
            .menu .nav a{
            font-size:110%;
            color:#464D5C;
            }
            .menu .nav li{
            margin:0px 20px 0px 20px;
            }
            .menu .current {
            background-color:#CCC;
            }
            .leftMenu {
            position: fixed;
            left:2%;
            top:10%;
            }
            .topbar .btn {
            border: 0;
            }
            .popularProducts li{

            }
            .productName{
            height:20px;
            margin: 15px 0px 15px 0px;
            background-color:whiteSmoke;
            font-size:110%;
            padding:2px 2px 2px 4px;
            }
            .brand {
            position:absolute;
            left:15px;
            }

        </style>
    </xsl:template>

    <xsl:template name="test">
        <html lang="en">
            <head>
                <meta charset="utf-8"/>
                <meta name="description" content=""/>
                <meta name="author" content=""/>

                <xsl:call-template name="title"/>
                <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
                <!--[if lt IE 9]>
                  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
                <![endif]-->

                <!-- Le styles -->
                <link href="bootstrap.css" rel="stylesheet"/>
                <!--<link href="../docs.css" rel="stylesheet">-->

                <xsl:call-template name="style"/>
            </head>
            <body>
                <div class="topbar">
                    <div class="fill">
                        <div class="container">
                            <a class="brand" href="/index.xml">GoodsReview</a>
                            <ul class="topMenu">
                                <li class="current">
                                    <a href="#">Главная</a>
                                </li>
                                <li>
                                    <a href="#about">О проекте</a>
                                </li>
                                <li>
                                    <a href="#tasks">Интересные задачи</a>
                                </li>
                                <li>
                                    <a href="#contact">Контакты</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="container">
                    <div class="content">
                        <div class="leftMenu">
                            <h3>Категории товаров</h3>
                            <ul class="leftMenuHead">
                                <!--<xsl:call-template name="categories"/>-->
                                <li>
                                    <a href="#">Ноутбуки</a>
                                </li>
                                <li>
                                    <a href="#">Мобильные телефоны</a>
                                </li>
                                <li>
                                    <a href="#">Настольные пк</a>
                                </li>
                                <li>
                                    <a href="#">Аудио-плееры</a>
                                </li>
                            </ul>

                        </div>

                        <div class="page-header">
                            <div class="row">
                                <div class="span2">
                                    <img src="images/mediumLogo.png"/>
                                </div>
                                <form method="get" action="/search.xml">
                                    <div class="span3">
                                        <div class="input">
                                            <input class="xlarge" id="query" name="query" size="30" type="text"
                                                   title="Спросите и Mr. ReviewMan подробно опишет вам товар"/>
                                        </div>
                                    </div>
                                    <div class="span1">
                                        <button class="btn">Подскажи!</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="replaceableContent">
                            <xsl:apply-templates/>
                        </div>
                    </div>

                    <footer>
                        <p>&#169; GoodsReview 2011</p>
                    </footer>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
