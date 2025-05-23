package com.yyjy;

import com.yyjy.event.ArticleCardEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest
public class PublishTests {

	@Autowired
	private ApplicationEventPublisher applicationEventPub;

	@Test
	public void testPublish() {
		applicationEventPub.publishEvent(new ArticleCardEvent(this, 2L));
	}
}
