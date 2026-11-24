# CIPHERX Step 2 - Pager Diagnostics

Target: WiFi Pineapple Pager Mark VII

## Requirements

- Android Termux
- OpenSSH
- Existing trusted SSH host key
- Authorized Pager access

## Configuration

Default host: 172.16.52.1
Default port: 22
Default user: root

Environment overrides:

PAGER_HOST
PAGER_PORT
PAGER_USER

## Run

bash ~/CIPHERX/scripts/pager-diagnostics.sh

## Security

- Strict SSH host-key verification
- No automatic host-key replacement
- No password storage
- Read-only diagnostic commands
- No payload execution
- No wireless attack commands
- Reports stay local and should not be committed

## Host Key Verification

Inspect the fingerprint presented by the Pager using a trusted
local device interface or console.

On Termux, inspect the presented SSH fingerprint:

ssh-keyscan -H 172.16.52.1 2>/dev/null | ssh-keygen -lf -

Only after independently confirming that fingerprint, add the
verified host key to the known_hosts file.

Never trust an unverified keyscan result.
