#!/usr/bin/env bash

set -eo pipefail

main() {
  if [ "$GITHUB_EVENT_NAME" == "pull_request" ];then
    log "checking ..."

  elif [ "$GITHUB_EVENT_NAME" == "push" ];then
    log "building ..."

    log "publishing new role packages ..."

  fi
}


main
