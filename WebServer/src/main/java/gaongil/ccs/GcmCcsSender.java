package gaongil.ccs;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class GcmCcsSender {

	private static final Logger log = LoggerFactory.getLogger(GcmCcsSender.class);

	private static final long PROJECT_ID = 342931063456L;
	private static final String API_KEY = "AIzaSyDKgha7E7VgCHQCu5JC4whgahlERlEiegM";
	private SmackCcsClient mCssClient = new SmackCcsClient();

	@PostConstruct
	public void init() {
		try {
			mCssClient.connect(PROJECT_ID, API_KEY);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	@Async
	public void send(String registrationId, String message) {
		try {
			Map<String, String> payload = new HashMap<String, String>();
			payload.put("msg", message);

			String jsonMessage = mCssClient.createJsonMessage(registrationId, mCssClient.nextMessageId(), payload, "", 10000L, true);
			mCssClient.sendDownstreamMessage(jsonMessage);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}
}