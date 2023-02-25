package com.sussysyrup.sussylib.resource;

import net.minecraft.resource.AbstractFileResourcePack;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class SussyResourcePack implements ResourcePack {
    private final String name;
    private static Set<String> namespaces = new HashSet<>();

    public SussyResourcePack(String name)
    {
        this.name = name;
    }

    public static void addNamespace(String namespace)
    {
        namespaces.add(namespace);
    }

    public static Map<Identifier, byte[]> clientResources = new HashMap<>();
    public static Map<Identifier, byte[]> serverResources = new HashMap<>();

    public static void registerClientResource(Identifier id, InputStream stream) throws IOException {

        clientResources.put(id, IOUtils.toByteArray(stream));
        stream.close();
    }

    public static void removeClientResources(Identifier id)
    {
        clientResources.remove(id);
    }

    public static void registerServerResource(Identifier id, InputStream stream) throws IOException {

        serverResources.put(id, IOUtils.toByteArray(stream));
        stream.close();
    }

    public static void removeServerResources(Identifier id)
    {
        clientResources.remove(id);
    }

    @Nullable
    @Override
    public InputSupplier<InputStream> openRoot(String... segments) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(Arrays.stream(segments).toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] bytes = bos.toByteArray();

        return () -> new ByteArrayInputStream(bytes);
    }

    //TODO
    @Nullable
    @Override
    public InputSupplier<InputStream> open(ResourceType type, Identifier id) {
        if(!namespaces.contains(id.getNamespace()))
        {
            return null;
        }

        if(type.equals(ResourceType.CLIENT_RESOURCES))
        {
            if(clientResources.containsKey(id))
            {
                return () -> new ByteArrayInputStream(clientResources.get(id));
            }
            return null;
        }
        else
        {
            if(serverResources.containsKey(id))
            {
                return () -> new ByteArrayInputStream(serverResources.get(id));
            }
            return null;
        }
    }

    @Override
    public void findResources(ResourceType type, String namespace, String prefix, ResultConsumer consumer) {
        if(!namespaces.contains(namespace))
        {
            return;
        }
        if(type.equals(ResourceType.CLIENT_RESOURCES))
        {
            for(Identifier id : clientResources.keySet())
            {
                if(id.getNamespace().equals(namespace) && id.getPath().startsWith(prefix))
                {
                    consumer.accept(id, () -> new ByteArrayInputStream(clientResources.get(id)));
                }
            }
        }
        else
        {

        }
    }

    @Override
    public Set<String> getNamespaces(ResourceType type) {
        return this.namespaces;
    }

    @Nullable
    @Override
    public <T> T parseMetadata(ResourceMetadataReader<T> metaReader) throws IOException {
        String jsonString = "{\n" +
                "  \"pack\": {\n" +
                "    \"pack_format\": 12,\n" +
                "    \"description\": \"Sussy Resource Pack\"\n" +
                "  }\n" +
                "}\n";
        try(InputStream stream = IOUtils.toInputStream(jsonString, Charset.defaultCharset())) {
            return AbstractFileResourcePack.parseMetadata(metaReader, stream);
        }
    }

    @Override
    public String getName() {
        return name;
    }


    //fine as is for now
    @Override
    public void close() {

    }
}
