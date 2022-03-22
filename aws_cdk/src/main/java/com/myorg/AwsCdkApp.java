package com.myorg;

import software.amazon.awscdk.App;

public class AwsCdkApp {

    public static void main(final String[] args) {
        App app = new App();

        new AwsVpcStack(app, "Vpc");

        app.synth();
    }
}

