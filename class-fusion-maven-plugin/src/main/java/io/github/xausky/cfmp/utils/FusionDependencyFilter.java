package io.github.xausky.cfmp.utils;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.shared.dependency.tree.DependencyNode;

/**
 * 依赖过滤器工具类
 * Created by xausky on 11/2/16.
 */
public class FusionDependencyFilter {
    /**
     * 判断是否需要解析该依赖
     * @param root 需要判断的依赖
     * @return 是否需要解析
     */
    public static boolean include(DependencyNode root) {
        for (DependencyNode node : root.getChildren()) {
            Artifact artifact = node.getArtifact();
            if(artifact.getGroupId().equals("io.github.xausky") &&
                    artifact.getArtifactId().equals("class-fusion-annotations")){
                return true;
            }
        }
        return false;
    }
}
