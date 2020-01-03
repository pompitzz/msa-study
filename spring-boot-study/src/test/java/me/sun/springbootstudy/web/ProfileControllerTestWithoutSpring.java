package me.sun.springbootstudy.web;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileControllerTestWithoutSpring {

    @Test
    void LookUp_local_profile() throws Exception {
        //given
        String expectedProfile = "local";
        MockEnvironment environment = new MockEnvironment();
        environment.addActiveProfile(expectedProfile);
        environment.addActiveProfile("real-db");

        ProfileController profileController = new ProfileController(environment);

        //when
        String profile = profileController.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    void LookUp_firstProfile() throws Exception {
        //given
        String expectedProfile = "real-db";
        MockEnvironment environment = new MockEnvironment();

        environment.addActiveProfile(expectedProfile);
        ProfileController profileController = new ProfileController(environment);

        //when
        String profile = profileController.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    void LookUp_Default_IfNotHaveActiveProfile() throws Exception {
        //given
        String expectedProfile = "default";
        MockEnvironment environment = new MockEnvironment();
        ProfileController profileController = new ProfileController(environment);

        //when
        String profile = profileController.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }


}