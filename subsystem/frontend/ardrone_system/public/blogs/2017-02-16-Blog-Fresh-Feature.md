# Added Blog auto update feature
*Hengjiu Kang Feb.2 2017*

# Introduction
In the last blog system like github, I used jekyll as pre-processor/compiler that generate website. But in the dynamic website like this, I integrate many features like a soup that it could be very hard to use off-the-shelf product. So eventually I came up with a very sketchy method to auto update fresh blogs from server over a REST api.

# Requirements
* REST API
* Nodejs
* Express
* Markdown render

# Implementation