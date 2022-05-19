#!/bin/bash
curl -X POST 'http://localhost:8080/airline' -H 'Content-Type: application/json' -d '{"query":"{helloWorld}"}'