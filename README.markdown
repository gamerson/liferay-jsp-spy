# JSP Spy Demo

## What is it?

In this Repo is a small POC of a tool that I've wanted for a long time.  That is a way to be able to
select a DOM node on a Liferay page and know what JSP in the portal rendered that DOM node.  So here it is. 
This repo contains:

- An [OSGi HTTP Whiteboard servlet filter](modules/jsp-spy/src/main/java/com/liferay/jsp/spy/JspSpyServletFilter.java) that is specially crafted HT: @rotty3000 to intercept every JSP include dispatch in the Liferay framework and injects some inert HTML markup.
- A very simple [Chrome Devtools extension](modules/jsp-spy-chrome-extension) that listens to Elements panel selection and displays the estimated JSP that rendered that node. This is not going to be correct 100% of the time and may require a little sleuthing through the nearby DOM.

## How to use it?

In order to use this the following 3 things are needed

- Set the following in your `portal-ext.properties` 
```
include-and-override=portal-developer.properties
direct.servlet.context.enabled=false
```
- Deploy the jsp-spy module
- Install the Chrome Extension in dev-mode.

## Give me the exact steps to play around with this

1. Clone this repo
2. run `blade server init`
3. run `blade server run`
4. run `blade deploy`
5. If you are using Chrome, go to [Extensions page](chrome://extensions)
6. Enable developer mode (right hand side)
7. Select "load unpacked" and browse to [jsp-spy-chrome-extension](modules/jsp-spy-chrome-extension) folder on disk
8. Open portal page, then open Chrome Devtools `Cmd+Option+I` 
9. Use element selection to select a DOM element on the page
10. On Elements Sidebar click the JSP Spy pane.

## Screenshots

Markup injected in HTML 
<img src="jsp-spy-markup.png" width="920" /> 

<br/><br/>

JSP Spy Elements Sidebar
<img src="jsp-spy-sidebar.png" width="920" /> 
