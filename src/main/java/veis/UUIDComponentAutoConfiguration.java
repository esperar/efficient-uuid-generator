package veis;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import veis.factory.UUIDFactory;
import veis.uuid1.EfficientUUID1Generator;

@AutoConfiguration
@ConditionalOnProperty(name = "efficient_uuid", havingValue = "on")
public class UUIDComponentAutoConfiguration {


    /**
     * We provide EfficientUUID1Generator other generator is not provided yet..
     * @return uuid v1 factory
     */
    @Bean
    public UUIDFactory uuidV1Factory() {
        return new UUIDFactory(new EfficientUUID1Generator());
    }
}
