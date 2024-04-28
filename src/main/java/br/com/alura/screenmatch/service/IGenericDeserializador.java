package br.com.alura.screenmatch.service;

public interface IGenericDeserializador {
    <T> T deserializa(String json, Class<T> classe);
}
