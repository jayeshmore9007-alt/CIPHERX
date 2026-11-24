#!/data/data/com.termux/files/usr/bin/bash
set -eu

HOST="${PAGER_HOST:-172.16.52.1}"
USER_NAME="${PAGER_USER:-root}"
PORT="${PAGER_PORT:-22}"

SSH_DIR="$HOME/.ssh"
KNOWN_HOSTS="$SSH_DIR/known_hosts"
REPORT_DIR="$HOME/CIPHERX/reports"

mkdir -p "$SSH_DIR" "$REPORT_DIR"
chmod 700 "$SSH_DIR"

if ! command -v ssh >/dev/null 2>&1; then
    echo "ERROR: OpenSSH is not installed."
    echo "Run: pkg install openssh"
    exit 1
fi

if ! command -v ssh-keygen >/dev/null 2>&1; then
    echo "ERROR: ssh-keygen is unavailable."
    exit 1
fi

if [ ! -f "$KNOWN_HOSTS" ] || ! ssh-keygen -F "$HOST" -f "$KNOWN_HOSTS" >/dev/null 2>&1; then
    echo "ERROR: Trusted host key not found."
    echo "Host: $HOST"
    echo ""
    echo "Verify the Pager host fingerprint independently."
    echo "Do not trust an unverified ssh-keyscan result."
    echo ""
    echo "No SSH connection attempted."
    exit 2
fi

REPORT="$REPORT_DIR/pager-$(date +%Y%m%d-%H%M%S).txt"

SSH_OPTIONS=(
    -p "$PORT"
    -o StrictHostKeyChecking=yes
    -o UserKnownHostsFile="$KNOWN_HOSTS"
    -o ConnectTimeout=8
    -o ServerAliveInterval=15
    -o ServerAliveCountMax=2
)

echo "CIPHERX PAGER DIAGNOSTICS"
echo "Host: $HOST"
echo "Port: $PORT"
echo "Mode: READ ONLY"
echo ""

{
    echo "CIPHERX PAGER DIAGNOSTICS"
    echo "Date: $(date)"
    echo "Host: $HOST"
    echo "Port: $PORT"
    echo "Mode: READ ONLY"
    echo "--------------------------------"

    COMMANDS=(
        "uptime"
        "uname -a"
        "ip addr"
        "ip route"
        "df -h"
        "free -h"
        "iw dev"
    )

    for CMD in "${COMMANDS[@]}"; do
        echo ""
        echo ">>> $CMD"

        if ssh "${SSH_OPTIONS[@]}" "$USER_NAME@$HOST" "$CMD"; then
            echo "RESULT: SUCCESS"
        else
            echo "RESULT: COMMAND FAILED"
        fi
    done

    echo ""
    echo "DIAGNOSTICS FINISHED"
} 2>&1 | tee "$REPORT"

echo ""
echo "Report saved: $REPORT"
echo "Remote configuration was not modified."
