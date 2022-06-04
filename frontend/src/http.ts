/* eslint-disable */

import { fetchEventSource } from '@microsoft/fetch-event-source';
import { FormEvent } from 'react';

export const createBaseHeaders = (): Record<string, string> => {
  return (window as any).jwt
    ? {
        Authorization: 'Bearer ' + (window as any).jwt,
      }
    : {};
};

export const get = (url: string) => {
  return fetch(url, { headers: createBaseHeaders() });
};

export const post = async (url: string, body: any) => {
  const headers: Record<string, string> = {
    ...createBaseHeaders(),
    'Content-Type': 'application/json',
  };

  const fetchRes = await fetch(url, {
    headers,
    method: 'POST',
    body: JSON.stringify(body),
  });

  if (
    Array.from(fetchRes.headers.entries())
      .find(([name]) => name === 'content-type')![1]
      .includes('text/plain')
  ) {
    return fetchRes.text();
  } else if (
    Array.from(fetchRes.headers.entries())
      .find(([name]) => name === 'content-type')![1]
      .includes('application/json')
  ) {
    return fetchRes.json();
  } else {
    return null;
  }
};

export const patch = async (url: string, body: any) => {
  const headers: Record<string, string> = {
    ...createBaseHeaders(),
    'Content-Type': 'application/json',
  };

  const fetchRes = await fetch(url, {
    headers,
    method: 'PATCH',
    body: JSON.stringify(body),
  });

  if (
    Array.from(fetchRes.headers.entries())
      .find(([name]) => name === 'content-type')![1]
      .includes('text/plain')
  ) {
    return fetchRes.text();
  } else if (
    Array.from(fetchRes.headers.entries())
      .find(([name]) => name === 'content-type')![1]
      .includes('application/json')
  ) {
    return fetchRes.json();
  } else {
    return null;
  }
};

export const deletee = async (url: string) => {
  const headers: Record<string, string> = {
    ...createBaseHeaders(),
  };

  const fetchRes = await fetch(url, {
    headers,
    method: 'DELETE',
  });

  if (
    Array.from(fetchRes.headers.entries())
      .find(([name]) => name === 'content-type')![1]
      .includes('text/plain')
  ) {
    return fetchRes.text();
  } else if (
    Array.from(fetchRes.headers.entries())
      .find(([name]) => name === 'content-type')![1]
      .includes('application/json')
  ) {
    return fetchRes.json();
  } else {
    return null;
  }
};

export const subscribe = async (e: FormEvent) => {
  e.preventDefault();
  const abortController = new AbortController();

  fetchEventSource(`/api/v1/notification-management/notifications/users`, {
    signal: abortController.signal,
    headers: {
      ...createBaseHeaders(),
    },
    onmessage: (message: any) => {
      console.log(message);
      console.log(JSON.parse(message.data));
    },
    openWhenHidden: true,
  });

  // setTimeout(() => {
  //   abortController.abort();
  //   console.log('abording')
  // }, 5000);
};
