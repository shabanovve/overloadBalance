#!/usr/bin/python3
# -*- coding: UTF-8 -*-

import argparse
import sys
import time
import requests


def wait_for():
    parser = argparse.ArgumentParser()
    parser.add_argument('url', nargs='?')
    namespace = parser.parse_args(sys.argv[1:])

    if namespace.url:
        url = namespace.url
    else:
        print ("You should define url")
        exit(1)
    while True:
        try:
            response = requests.get(url, timeout=(1, 1))
            if response.status_code == 200:
                print('Got code 200 from {}'.format(url))
                break
            else:
                print(response)
                time.sleep(1)
        except Exception:
            print("Wait for {}".format(url))
            time.sleep(1)


if __name__ == "__main__":
    wait_for()
