package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.tendersystem.AdvertisementBanner;
import com.jojolaptech.camel.model.postgres.enums.StatusEnum;
import com.jojolaptech.camel.model.postgres.storage.AdvertisementBannerEntity;
import com.jojolaptech.camel.repository.postgres.storage.AdvertisementBannerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdvertisementBannerProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(AdvertisementBannerProcessor.class);

    private final AdvertisementBannerRepository advertisementBannerRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        AdvertisementBanner source = exchange.getMessage().getMandatoryBody(AdvertisementBanner.class);

        log.info("Migrating advertisement_banner mysql id={}", source.getId());

        if (advertisementBannerRepository.existsByMysqlId(source.getId())) {
            log.info("Skipping advertisement_banner id={}, already exists", source.getId());
            return;
        }

        AdvertisementBannerEntity target = new AdvertisementBannerEntity();
        target.setMysqlId(source.getId());
        target.setDescription(source.getDescription());
        target.setAdvertisementUrl(mergeTopThenMiddle(source.getUrlTop(), source.getUrl()));
        target.setBanner(mergeTopThenMiddle(source.getBannerImageTop(), source.getBannerImage()));
        target.setStatus(mapStatus(source.getIsActive()));

        AdvertisementBannerEntity saved = advertisementBannerRepository.save(target);
        log.info("Saved advertisement_banner to Postgres id={}, mysqlId={}", saved.getId(), saved.getMysqlId());
    }

    /**
     * Same merge as migrate-advertisement-banner-single-slot.sql:
     * COALESCE(NULLIF(TRIM(top), ''), middle).
     */
    private static String mergeTopThenMiddle(String top, String middle) {
        String fromTop = trimToNull(top);
        if (fromTop != null) {
            return fromTop;
        }
        return trimToNull(middle);
    }

    private static String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String t = value.trim();
        return t.isEmpty() ? null : t;
    }

    private static StatusEnum mapStatus(Boolean isActive) {
        if (Boolean.FALSE.equals(isActive)) {
            return StatusEnum.INACTIVE;
        }
        return StatusEnum.ACTIVE;
    }
}
