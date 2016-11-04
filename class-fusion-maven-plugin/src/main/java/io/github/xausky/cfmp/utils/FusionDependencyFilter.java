package io.github.xausky.cfmp.utils;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.shared.dependency.tree.DependencyNode;

/**
 * Created by xausky on 11/2/16.
 */
public class FusionDependencyFilter {
    public static boolean include(DependencyNode root) {
        for (DependencyNode node : root.getChildren()) {
            Artifact artifact = node.getArtifact();
            if(artifact.getGroupId().equals("io.github.xausky") && artifact.getArtifactId().equals("class-fusion-annotations")){
                return true;
            }
        }
        return false;
    }
}
