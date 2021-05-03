package com.example.test.soap;

import com.example.test.common.util.MapBuilder;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

import java.io.StringReader;

import io.reactivex.rxjava3.core.Single;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class TestSoap {
    public static void main(String[] args) {
        Single.fromFuture(Unirest.post("http://www.dneonline.com/calculator.asmx")
                    .contentType("text/xml")
                    .body("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "   <soapenv:Header/>\n" +
                            "   <soapenv:Body>\n" +
                            "      <tem:Subtract xmlns:tem=\"http://tempuri.org/\">\n" +
                            "         <tem:intA>13</tem:intA>\n" +
                            "         <tem:intB>4</tem:intB>\n" +
                            "      </tem:Subtract>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>")
                    .asStringAsync())
                .map(HttpResponse::getBody)
                .subscribe(TestSoap::parse, Throwable::printStackTrace);
    }

    private static void parse(final String xml) throws DocumentException {
        final SAXReader reader = new SAXReader();
        reader.getDocumentFactory().setXPathNamespaceURIs(MapBuilder.of("soap", "http://schemas.xmlsoap.org/soap/envelope/", "s", "http://tempuri.org/"));
        final Document document = reader.read(new StringReader(xml));
        final Namespace soapNs = new Namespace("soap", "http://schemas.xmlsoap.org/soap/envelope/");
        document.getRootElement().elements(new QName("Body", soapNs)).forEach(e -> System.out.println(e.getQName()));
        document.selectNodes("//soap:Envelope/soap:Body/s:SubtractResponse/s:SubtractResult").forEach(n -> System.out.println(n.getText()));
//            document.getRootElement().selectNodes("//soap:Body").forEach(node -> System.out.println(node.getName()));
    }

    public static class ElementBuilder {
        private QName qName;

        private ElementBuilder() {}
        private ElementBuilder(final QName qName) {
            this.qName = qName;
        }

        public static ElementBuilder builder(final String name) {
            return new ElementBuilder(new QName(name));
        }
    }
}
