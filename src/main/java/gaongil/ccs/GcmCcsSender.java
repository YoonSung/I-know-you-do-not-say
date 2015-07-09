package gaongil.ccs;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.fasterxml.jackson.databind.ObjectMapper;
import gaongil.domain.User;
import gaongil.dto.cloud.CloudMessage;
import org.jivesoftware.smack.SmackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class GcmCcsSender {

	private static final Logger log = LoggerFactory.getLogger(GcmCcsSender.class);

	private static final long PROJECT_ID = 342931063456L;
	private static final String API_KEY = "AIzaSyDKgha7E7VgCHQCu5JC4whgahlERlEiegM";

	@Autowired
	private SmackCcsClient mCssClient;

	@Autowired
	private ObjectMapper objectMapper;

	@PostConstruct
	public void init() {
		try {
			mCssClient.connect(PROJECT_ID, API_KEY);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	/*
	@Async
	public void send(String registrationId, String message) {
		try {
			Map<String, String> payload = new HashMap<String, String>();
			payload.put("msg", message);

			@SuppressWarnings("static-access")
			String jsonMessage = mCssClient.createJsonMessage(registrationId, mCssClient.nextMessageId(), payload, "", 10000L, true);
			mCssClient.sendDownstreamMessage(jsonMessage);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}
	*/

	@Async
	public void send(User user, CloudMessage cloudMessage) {

		Assert.notNull(user.getRegId());
		Assert.notNull(cloudMessage);

		try {
			Map<String, String> payload = new HashMap<String, String>();
			payload.put("msg", objectMapper.writeValueAsString(cloudMessage));

			String jsonMessage = mCssClient.createJsonMessage(user.getRegId(), mCssClient.nextMessageId(), payload, "", 10000L, true);
			mCssClient.sendDownstreamMessage(jsonMessage);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			throw new RuntimeException();
		}
	}


	@PreDestroy
	public void destroy() {
		try {
			mCssClient.disconnect();
		} catch (SmackException.NotConnectedException e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}