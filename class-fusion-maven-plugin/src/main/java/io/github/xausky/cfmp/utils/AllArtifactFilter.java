package io.github.xausky.cfmp.utils;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by xausky on 11/2/16.
 */
public class AllArtifactFilter implements ArtifactFilter {
    @Override
    public boolean include(Artifact artifact) {
        return true;
    }
}
