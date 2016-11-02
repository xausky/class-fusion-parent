package io.github.xausky.cfmp.utils;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by xausky on 11/2/16.
 */
public class RegexArtifactFilter implements ArtifactFilter {
    private Pattern groupPattern;
    private Pattern artifactPattern;
    public RegexArtifactFilter(String groupRegex,String artifactRegex){
        groupPattern = Pattern.compile(groupRegex);
        artifactPattern = Pattern.compile(artifactRegex);
    }

    @Override
    public boolean include(Artifact artifact) {
        boolean groupMatches = groupPattern.matcher(artifact.getGroupId()).matches();
        boolean artifactMatches = artifactPattern.matcher(artifact.getArtifactId()).matches();
        List<String> trails = artifact.getDependencyTrail();
        System.out.println("artifact: "+artifact.toString());
        for(String trail:trails){
            System.out.println("trail: -- "+trail);
        }
        return groupMatches && artifactMatches;
    }
}
