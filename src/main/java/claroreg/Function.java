package claroreg;

import com.google.gson.JsonObject;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    @FunctionName("base_path")
    public HttpResponseMessage run_base (
            @HttpTrigger(
                    name = "req",
                    route = "{resource}/{method}/{arg1=EMPTY}/{arg2=EMPTY}/{arg3=EMPTY}",
                    methods = {
                            HttpMethod.GET,
                            HttpMethod.POST,
                            HttpMethod.PUT,
                            HttpMethod.DELETE,
                            HttpMethod.HEAD,
                            HttpMethod.OPTIONS,
                            HttpMethod.TRACE,
                            HttpMethod.CONNECT
                    },
                    authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context,
            @BindingName("resource") String resource,
            @BindingName("method") String method,
            @BindingName("arg1") String arg1,
            @BindingName("arg2") String arg2,
            @BindingName("arg3") String arg3
    ){
        context.getLogger().info("Java HTTP trigger processed a request.");

        JsonObject r = new JsonObject();
        r.addProperty("resource", resource);
        r.addProperty("method", method);
        r.addProperty("arg1", arg1);
        r.addProperty("arg2", arg2);
        r.addProperty("arg3", arg3);

        return request.createResponseBuilder(HttpStatus.OK).body(r).build();

    }
}
