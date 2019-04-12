# Liferay JSP Spy

## What is it?

Have you ever seen some html content in a Liferay page and wondered what JSP rendered that content?  You can use JSP Spy to find out.  If you have heard enough, just to the [How to use it](#how-to-use-it) section.

For those that are interested, this repo contains:

- An [OSGi HTTP Whiteboard servlet filter](modules/jsp-spy/src/main/java/com/liferay/jsp/spy/JspSpyServletFilter.java) that is specially crafted HT: @rotty3000 to intercept every JSP include dispatch in the Liferay framework and injects some inert HTML markup.
- Some [small configuration](configs/local/portal-ext.properties) that is required for Liferay Portal.
- A simple [WebExtension](modules/jsp-spy-extension) that listens to Elements panel selection and displays the estimated JSP that rendered that node. This is not going to be correct 100% of the time and may require a little sleuthing through the nearby DOM. I hope to improve this and am welcoming contributions.

## How to use it?

In order to use this the following 3 things are needed

- Set the following in your `portal-ext.properties` 
```
include-and-override=portal-developer.properties
direct.servlet.context.enabled=false
```
- Build and deploy the jsp-spy module
- Build the Chrome extension
- Install the Chrome Extension in developer module.

## Give me the exact steps to play around with this

1. Clone this repo
2. run `blade server init`
3. run `blade server run`
4. run `blade deploy`
5. cd `modules/jsp-spy-extension`
6. run `yarn install`
7. run `yarn dev chrome`
5. If you are using Chrome, go to [Extensions page](chrome://extensions)
6. Enable developer mode (right hand side)
7. Select "load unpacked" and browse to `modules/jsp-spy-extension/dist/chrome` folder
8. Open portal page, then open Chrome Devtools `Cmd+Option+I` 
9. Use element selection to select a DOM element on the page
10. On Elements Sidebar click the JSP Spy pane.

## Screenshots

Markup injected in HTML 
<img src="jsp-spy-markup.png" width="920" /> 

<br/><br/>

JSP Spy Elements Sidebar
<img src="jsp-spy-sidebar.png" width="920" /> 
