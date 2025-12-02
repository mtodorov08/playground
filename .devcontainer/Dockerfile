FROM mcr.microsoft.com/devcontainers/java:17

# Install Maven (works on Debian-based devcontainer base images)
USER root
RUN apt-get update \
    && apt-get install -y maven \
    && rm -rf /var/lib/apt/lists/*

USER vscode
