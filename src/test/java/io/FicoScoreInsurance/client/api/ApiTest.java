package io.FicoScoreInsurance.client.api;

import io.FicoScoreInsurance.client.ApiException;
import io.FicoScoreInsurance.client.model.Persona;
import io.FicoScoreInsurance.client.model.Respuesta;
import io.FicoScoreInsurance.interceptor.SignerInterceptor;
import okhttp3.OkHttpClient;
import io.FicoScoreInsurance.client.ApiClient;
import io.FicoScoreInsurance.client.api.ApiTest;
import io.FicoScoreInsurance.client.api.FicoScoreInsuranceApi;
import io.FicoScoreInsurance.client.model.Domicilio;
import io.FicoScoreInsurance.client.model.Domicilio.EstadoEnum;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiTest {
	
	private Logger logger = LoggerFactory.getLogger(ApiTest.class.getName());
    private final FicoScoreInsuranceApi api = new FicoScoreInsuranceApi();
    private final SignerInterceptor interceptor = new SignerInterceptor();
	private ApiClient apiClient = null;	
    
	@Before()
	public void setUp() {
		this.apiClient = api.getApiClient();
		this.apiClient.setBasePath("the_url");
    	OkHttpClient insecureClient = ApiClient.getClientNoSSLVerification();
    	OkHttpClient okHttpClient = insecureClient.newBuilder()
    			.readTimeout(60, TimeUnit.SECONDS)
    			.addInterceptor(interceptor)
    			.build();
    	apiClient.setHttpClient(okHttpClient);
	}

    @Test
    public void getFicoscoreTest() throws ApiException {    	
    	String xApiKey = "your_api_key";
    	String username = "your_username";
    	String password = "your_password";
    	
    	Persona requestPersona = new Persona();
    	Domicilio requestDomicilio = new Domicilio();

        requestDomicilio.setDireccion(null);
        requestDomicilio.setColonia(null);
        requestDomicilio.setCiudad(null);
        requestDomicilio.setCodigoPostal(null);
        requestDomicilio.setMunicipio(null);
        requestDomicilio.setEstado(EstadoEnum.AGS);

        requestPersona.setPrimerNombre("NOMBRE");
        requestPersona.setSegundoNombre(null);
        requestPersona.setApellidoPaterno("PATERNO");
        requestPersona.setApellidoMaterno("MATERNO");
        requestPersona.setApellidoAdicional(null);
        requestPersona.setFechaNacimiento("07-01-1980");
        requestPersona.setRfc(null);
        requestPersona.setCurp(null);
        requestPersona.setDomicilio(requestDomicilio);
        
		try {
			Respuesta response = api.getFicoscore(xApiKey, username, password, requestPersona);
	        Assert.assertTrue(response != null);
	        if(response != null) {
	        	logger.info(response.toString());
	        }
		} catch (ApiException e) {
			logger.info(e.getResponseBody());
		}        
    }
}