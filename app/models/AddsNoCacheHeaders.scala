package models;

import play.api.mvc.Result;

trait AddsNoCacheHeaders {
    def addNoCacheHeaders(result: Result): Result = {
        result.withHeaders("Cache-Control" -> "no-cache, no-store, must-revalidate",
        "Pragma" -> "no-cache",
        "Expires" -> "0")
    }
}