package com.myorg;

import software.amazon.awscdk.App;

public class AwsCdkApp {

    public static void main(final String[] args) {
        App app = new App();

        AwsVpcStack awsVpcStack = new AwsVpcStack(app, "Vpc");

        AwsClusterStack awsClusterStack = new AwsClusterStack(app, "Cluster", awsVpcStack.getVpc());
        awsClusterStack.addDependency(awsVpcStack);

        app.synth();
    }
}

