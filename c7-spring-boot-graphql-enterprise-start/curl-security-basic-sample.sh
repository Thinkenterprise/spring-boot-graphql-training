#!/bin/bash
curl -u user:password -X POST 'http://localhost:8080/graphql' -H 'Content-Type: application/json' -d '{"query":"{routes{id}}"}'
