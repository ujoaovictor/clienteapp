package com.clienteapp.service;

import com.clienteapp.model.ViaCepResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

public class ViaCepService {

    
    private static final String BASE_URL = "https://viacep.com.br/ws/";

    public Optional<ViaCepResponse> buscarCep(String cep) {

        
        String cepLimpo = cep.replaceAll("[^0-9]", "");

        
        if (cepLimpo.length() != 8) {
            System.out.println(" CEP inválido. Digite 8 números.");
            return Optional.empty();
        }

        try {
            
            String urlStr = BASE_URL + cepLimpo + "/json/";
            URL url = new URL(urlStr);

            
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");         
            conn.setConnectTimeout(5000);        
            conn.setReadTimeout(5000);         

            
            int statusCode = conn.getResponseCode();
            if (statusCode != 200) {
                System.out.println(" Erro na API. Código: " + statusCode);
                return Optional.empty();
            }

            
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
            );
            StringBuilder resposta = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                resposta.append(linha);
            }
            reader.close();

            
            Gson gson = new Gson();
            ViaCepResponse viaCep = gson.fromJson(resposta.toString(), ViaCepResponse.class);

      
            if (viaCep.isErro()) {
                System.out.println(" CEP não encontrado.");
                return Optional.empty();
            }

            return Optional.of(viaCep);

        } catch (Exception e) {
            System.out.println(" Erro ao consultar ViaCEP: " + e.getMessage());
            return Optional.empty();
        }
    }
}