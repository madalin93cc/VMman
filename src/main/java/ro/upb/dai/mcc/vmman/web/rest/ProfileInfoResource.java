package ro.upb.dai.mcc.vmman.web.rest;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.upb.dai.mcc.vmman.config.DefaultProfileUtil;
import ro.upb.dai.mcc.vmman.config.VmmanProperties;

import javax.inject.Inject;

/**
 * Resource to return information about the currently running Spring profiles.
 */
@RestController
@RequestMapping("/api")
public class ProfileInfoResource {

    @Inject
    private Environment env;

    @Inject
    private VmmanProperties vmmanProperties;

    @GetMapping("/profile-info")
    public ProfileInfoResponse getActiveProfiles() {
        String[] activeProfiles = DefaultProfileUtil.getActiveProfiles(env);
        return new ProfileInfoResponse(activeProfiles);
    }

    class ProfileInfoResponse {

        public String[] activeProfiles;

        ProfileInfoResponse(String[] activeProfiles) {
            this.activeProfiles = activeProfiles;
        }
    }
}
