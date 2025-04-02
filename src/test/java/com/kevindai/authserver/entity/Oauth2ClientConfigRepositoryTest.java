package com.kevindai.authserver.entity;


import com.kevindai.authserver.repository.Oauth2ClientConfigRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class Oauth2ClientConfigRepositoryTest {

    @Autowired
    private Oauth2ClientConfigRepository repository;

    @Test
    void findById_existingId_returnsEntity() {
        Optional<Oauth2ClientConfigEntity> foundEntity = repository.findById(1);

        assertThat(foundEntity).isPresent();
        assertThat(foundEntity.get().getId()).isEqualTo(1);
        assertThat(foundEntity.get().getClientId()).isEqualTo("kevintest01");
        assertThat(foundEntity.get().getGrantTypes().size()).isEqualTo(3);
    }

    @Test
    void findById_nonExistingId_returnsEmpty() {
        Optional<Oauth2ClientConfigEntity> foundEntity = repository.findById(999);

        assertThat(foundEntity).isNotPresent();
    }

    @Test
    void save_validEntity_savesSuccessfully() {
        Oauth2ClientConfigEntity entity = new Oauth2ClientConfigEntity();
        entity.setClientId("clientIdTest");
        entity.setClientSecretHash("kevindaiHash");

        Oauth2ClientConfigEntity savedEntity = repository.save(entity);

        assertThat(savedEntity).isNotNull();
        assertThat(savedEntity.getClientSecretHash()).isEqualTo("kevindaiHash");
    }

    @Test
    void deleteById_existingId_deletesSuccessfully() {
        Oauth2ClientConfigEntity entity = new Oauth2ClientConfigEntity();
        entity.setClientId("clientIdTest");
        entity.setClientSecretHash("kevindaiHash");
        Oauth2ClientConfigEntity savedEntity = repository.save(entity);

        repository.deleteById(savedEntity.getId());
        Optional<Oauth2ClientConfigEntity> foundEntity = repository.findById(3);
        assertThat(foundEntity).isNotPresent();
    }
}
