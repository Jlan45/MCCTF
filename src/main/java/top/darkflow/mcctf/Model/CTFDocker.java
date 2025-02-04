package top.darkflow.mcctf.Model;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import top.darkflow.mcctf.Docker.DockerConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static top.darkflow.mcctf.Tools.GenerateFlag;

public class CTFDocker {
    private String Hash;
    private UUID Team;
    private String Image;
    private String[] Port;
    private UUID Challenge;

    public CTFDocker( UUID team, String image, String[] port, UUID challenge) {
        Team = team;
        Image = image;
        Port = port;
        Challenge = challenge;
    }
    public UUID getChallenge() {
        return Challenge;
    }

    public void setChallenge(UUID challenge) {
        Challenge = challenge;
    }

    public String[] getPort() {
        return Port;
    }

    public void setPort(String[] port) {
        Port = port;
    }

    public UUID getTeam() {
        return Team;
    }

    public void setTeam(UUID team) {
        Team = team;
    }

    public String getHash() {
        return Hash;
    }

    public void setHash(String hash) {
        Hash = hash;
    }
    public boolean startDockerContainer(){
        //start docker container
        ArrayList<PortBinding> pblist = new ArrayList<>();
        for(String port:Port){
            pblist.add(PortBinding.parse(port));
        }
        CreateContainerResponse container = DockerConnection.dockerClient.createContainerCmd(Image)
                .withName(UUID.randomUUID().toString())
                .withHostConfig(HostConfig.newHostConfig()
                        .withPortBindings(pblist))
                .withEnv("MCCTF_FLAG="+GenerateFlag(Team,Challenge))
                .exec();
        this.setHash(container.getId());
        DockerConnection.dockerClient.startContainerCmd(container.getId()).exec();
        return true;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
