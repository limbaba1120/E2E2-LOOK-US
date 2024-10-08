import { ApiError } from 'next/dist/server/api-utils'
import { API_PRIVATE_URL } from '../_common/constants'
import { ProfileImage } from '../(without-login)/signup/page'

//회원 정보를 불러옵니다 -> 프로필 정보 가져오기
// TODO: change this to preview dto later...
export interface myInfoAllResponse {
    username: string
    email: string
    gender: string
    birth: string
    nickname: string
    instaId: string
    imageLocationId: number
    postNum: number
}

export interface myPostAllResponse {
    imageLocationId: number
    postContent: string
    hashtags: string[]
    likeCount: number
    postId: number
}

export async function myInfoAllFunction() {
    const requestUrl = `${API_PRIVATE_URL}/user/me`
    console.log('1')

    const res = await fetch(requestUrl, {
        method: 'GET',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json', // 요청의 콘텐츠 타입을 JSON으로 설정
        },
    })

    if (!res.ok) {
        const body = await res.json()
        throw new ApiError(res.status, body)
    }

    const body = await res.json()
    return body as myInfoAllResponse
}

export async function updateProfileInfo(
    updateRequest: {
        birth: string
        instaId: string
        nickName: string
        gender: string
    },
    profileImage?: ProfileImage | null,
) {
    const formData = new FormData()

    const updateRequestBlob = new Blob([JSON.stringify(updateRequest)], {
        type: 'application/json',
    })
    formData.append('updateRequest', updateRequestBlob) // JSON 데이터를 문자열로 변환하여 추가

    if (profileImage) {
        formData.append('profileImage', profileImage.blob)
    }

    const requestUrl = `${API_PRIVATE_URL}/user/update`

    const res = await fetch(requestUrl, {
        method: 'PATCH',
        credentials: 'include',
        body: formData,
    })

    if (!res.ok) {
        const errorBody = await res.json()
        throw new ApiError(res.status, errorBody)
    }

    return await res.json()
}

export async function updateProfileImg(profileImage: File) {
    const formData = new FormData()
    formData.append('profileImage', profileImage)
    const requestUrl = `${API_PRIVATE_URL}/user/update`

    const res = await fetch(requestUrl, {
        method: 'PATCH',
        credentials: 'include',
        body: formData,
    })

    if (!res.ok) {
        const errorBody = await res.json()
        throw new ApiError(res.status, errorBody)
    }

    return await res.json()
}

export async function getMyPosts(): Promise<myPostAllResponse[]> {
    const requestUrl = `${API_PRIVATE_URL}/user/me/posts`

    const res = await fetch(requestUrl, {
        method: 'GET',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json', // 요청의 콘텐츠 타입을 JSON으로 설정
        },
    })

    if (!res.ok) {
        const body = await res.json()
        throw new ApiError(res.status, body)
    }

    const body = await res.json()
    console.log(body)
    return body as myPostAllResponse[]
}
