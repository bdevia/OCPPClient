package com.example.request;

import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.OcppConnection;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import eu.chargetime.ocpp.CallErrorException;
import eu.chargetime.ocpp.JSONClient;
import eu.chargetime.ocpp.model.Request;

public class RequestHandler extends ErrorHandler {
    
    public static CompletableFuture<JsonNode> sendRequest(Request request){
        JSONClient client = OcppConnection.getInstance().getClient();
        CompletableFuture<JsonNode> resultFuture = new CompletableFuture<>();

        try{
            client.send(request).whenComplete((result, ex) -> {
                if(ex != null && ex instanceof CallErrorException){
                    CallErrorException callErrorException = (CallErrorException) ex;
                    System.out.println(callErrorException.getErrorCode());
                    resultFuture.complete(failRequest(callErrorException.getErrorCode()));
                }
                else if(ex != null){
                        System.out.println(ex.getMessage());
                        resultFuture.complete(internalError(ex.getMessage()));
                }   
                else{
                    System.out.println();
                    System.out.println("Result: " + result);
                    System.out.println();
                    resultFuture.complete(parseStringToJsonNode(result.toString()));
                }
            });
        } 
        catch(Exception e) {
            e.printStackTrace();
            resultFuture.complete(internalError(e.getMessage()));
        }
        return resultFuture;
    }

    public static JsonNode parseStringToJsonNode(String input) {
        // Inicializa un ObjectMapper para crear objetos JsonNode
        ObjectMapper mapper = new ObjectMapper();
        JsonNodeFactory factory = mapper.getNodeFactory();

        // Crea un objeto ObjectNode para almacenar los datos JSON
        ObjectNode jsonNode = factory.objectNode();

        // Expresión regular para buscar los campos y valores dentro de las llaves {}
        String regex = "\\{([^\\}]+)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            // Obtiene el contenido dentro de las llaves {}
            String[] keyValuePairs = matcher.group(1).split(", ");

            // Itera sobre los pares clave-valor y los agrega al objeto ObjectNode
            for (String pair : keyValuePairs) {
                String[] keyValue = pair.split("=");
                String key = keyValue[0];
                String value = keyValue[1];

                // Elimina las comillas dobles si están presentes
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }

                // Agrega el par clave-valor al objeto ObjectNode
                jsonNode.put(key, value);
            }
        }

        return jsonNode;
    }

}
