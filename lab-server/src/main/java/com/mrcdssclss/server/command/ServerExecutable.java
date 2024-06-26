package com.mrcdssclss.server.command;

import com.mrcdssclss.common.Request;
import com.mrcdssclss.common.Response;

import java.io.IOException;

public interface ServerExecutable {
    public Response execute(Request request) throws IOException;

}
