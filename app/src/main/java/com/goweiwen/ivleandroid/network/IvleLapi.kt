package com.goweiwen.ivleandroid.network

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import com.goweiwen.ivleandroid.APIKEY

/**
 * Created by weiwen on 1/26/17.
 */
class IvleLapi(authToken: String) {
    lateinit var authToken: String

    init {
        this.authToken = authToken
    }

    fun get(query: String, params: Map<String, Any>): Result<Json, FuelError> {
        var url = StringBuilder("https://ivle.nus.edu.sg/api/Lapi.svc/Validate?APIKey=$APIKEY&Token=$authToken")
        for ((key, value) in params) {
            url.append("&$key=$value")
        }

        val (request, response, result) = Fuel.get(url.toString()).responseJson()
        return result
    }

    // Login

    fun validate(): Result<Json, FuelError> =
            get("Validate", mapOf())

    fun username(): Result<Json, FuelError> =
            get("UserName_Get", mapOf())

    fun userId(): Result<Json, FuelError> =
            get("UserID_Get", mapOf())

    fun userEmail(): Result<Json, FuelError> =
            get("UserEmail_Get", mapOf())

    // Module

    fun modules(duration: Int, includeAllInfo: Boolean): Result<Json, FuelError> =
            get("Modules", mapOf("duration" to duration, "includeAllInfo" to includeAllInfo))

    fun modulesStaff(duration: Int, includeAllInfo: Boolean): Result<Json, FuelError> =
            get("Modules_Staff", mapOf("duration" to duration, "includeAllInfo" to includeAllInfo))

    fun modulesStudent(duration: Int, includeAllInfo: Boolean): Result<Json, FuelError> =
            get("Modules_Student", mapOf("duration" to duration, "includeAllInfo" to includeAllInfo))

    fun module(duration: Int, includeAllInfo: Boolean, courseId: String, titleOnly: Boolean)
            : Result<Json, FuelError> =
            get("Module", mapOf("duration" to duration, "includeAllInfo" to includeAllInfo, "courseId" to courseId, "titleOnly" to titleOnly))

    fun moduleLecturers(duration: Int, includeAllInfo: Boolean): Result<Json, FuelError> =
            get("Modules_Lecturers", mapOf("duration" to duration, "includeAllInfo" to includeAllInfo))

    fun displayPhoto(courseId: String, userId: String): Result<Json, FuelError> =
            get("DisplayPhoto", mapOf("courseId" to courseId, "userId" to userId))

    fun moduleInformation(duration: Int, courseId: String): Result<Json, FuelError> =
            get("Module_Information", mapOf("duration" to duration, "courseId" to courseId))

    fun moduleWeblinks(courseId: String): Result<Json, FuelError> =
            get("Module_Weblinks", mapOf("courseId" to courseId))

    fun moduleReading(duration: Int, includeAllInfo: Boolean): Result<Json, FuelError> =
            get("Module_Reading", mapOf("duration" to duration, "includeAllInfo" to includeAllInfo))

    // Announcements

    fun announcements(courseId: String, duration: Int, titleOnly: Boolean) =
            get("Announcements", mapOf("courseId" to courseId, "duration" to duration, "titleOnly" to titleOnly))

    // Workbin

    fun workbins(courseId: String, duration: Int, workbinId: String, titleOnly: Boolean) =
            get("Workbins", mapOf("courseId" to courseId, "duration" to duration, "workbinId" to workbinId, "titleOnly" to titleOnly))

    fun downloadFile(id: String) = get("DownloadFile", mapOf("id" to id))
}