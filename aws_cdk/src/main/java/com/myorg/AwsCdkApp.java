package com.myorg;

import software.amazon.awscdk.App;

public class AwsCdkApp {

    public static void main(final String[] args) {
        App app = new App();

        AwsVpcStack awsVpcStack = new AwsVpcStack(app, "Vpc");

        AwsClusterStack awsClusterStack = new AwsClusterStack(app, "Cluster", awsVpcStack.getVpc());
        awsClusterStack.addDependency(awsVpcStack);

        AwsRdsStack awsRdsStack = new AwsRdsStack(app, "Rds", awsVpcStack.getVpc());
        awsRdsStack.addDependency(awsVpcStack);

        AwsSnsStack awsSnsStack = new AwsSnsStack(app, "Sns");

        AwsService01Stack awsService01Stack = new AwsService01Stack(app, "Service01", awsClusterStack.getCluster());
        awsService01Stack.addDependency(awsClusterStack);
        awsService01Stack.addDependency(awsRdsStack);

        app.synth();
    }
}

