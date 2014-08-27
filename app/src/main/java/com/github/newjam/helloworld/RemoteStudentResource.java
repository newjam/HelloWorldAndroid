package com.github.newjam.helloworld;

import java.util.List;

import retrofit.http.GET;

/**
 * Created by newjam on 8/27/14.
 */
public interface RemoteStudentResource {
    @GET("/students")
    List<Student> listStudents();
}
