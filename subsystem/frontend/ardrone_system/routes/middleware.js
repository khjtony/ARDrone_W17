/**
 * Created by khjtony on 2/15/17.
 */
var express = require('express');

var middleware = function(){};

middleware.blog_fresh = function () {
    return "hahaha";
}

module.exports = middleware;